package com.pg.iloveblog.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pg.iloveblog.dto.BoardPagenationDTO;
import com.pg.iloveblog.model.AttachFile;
import com.pg.iloveblog.model.Board;
import com.pg.iloveblog.model.Reply;
import com.pg.iloveblog.model.User;
import com.pg.iloveblog.repository.AttachFileRepository;
import com.pg.iloveblog.repository.BoardRepository;
import com.pg.iloveblog.repository.ReplyRepository;

import jdk.internal.org.jline.utils.Log;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardService {
	@Resource
	private BoardRepository boardRepository;
	@Resource
	private ReplyRepository replyRepository;
	@Resource
	private AttachFileRepository attachFileRepository;
	private final int DISPLAY_PAGE_NUMBER = 10;
	
	@Transactional
	public Board 글쓰기(Board board, User user) {
		board.setCount(0);
		board.setUser(user);
		Board result = boardRepository.save(board);
		List<AttachFile> resultFiles = 첨부파일저장(board.getAttachFiles(), result);
		result.setAttachFiles(resultFiles);
		return result;
	}
	
	@Transactional(readOnly = true)
	public BoardPagenationDTO 글목록(Pageable pageable){
		Page<Board> boards = boardRepository.findAll(pageable);
		//현재페이지
		int currentPage = boards.getPageable().getPageNumber()+1; //0부터 시작(+1해준 이유가 0부터 시작해서)
		//총 게시물 수
		long totalContents = boards.getTotalElements();
		//페이지당 보여지는 수
		int pageSize = boards.getPageable().getPageSize();
		
		//페이징
		int endPage = (int)Math.ceil((double)currentPage/DISPLAY_PAGE_NUMBER)*DISPLAY_PAGE_NUMBER;
		int startPage = endPage - DISPLAY_PAGE_NUMBER + 1; 
		int realEndPage = (int)Math.ceil((double)totalContents/pageSize);
		log.debug("currentPage:%d, totalContents:%d, ",currentPage,totalContents);
		log.debug("endPage:%d, startPage:%d, realEndpage:%d \n",endPage,startPage,realEndPage);
		if(realEndPage < endPage) endPage = realEndPage;
		boolean isNext = realEndPage==endPage?false:true;//Next버튼 유무 (true:유, false:무)
		boolean isPrev = endPage <= DISPLAY_PAGE_NUMBER?false:true; //Prev버튼 유무(true:유, false:무)
		
		SimpleDateFormat sdfOld = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfToDay = new SimpleDateFormat("HH:mm:ss");
		String dateDisplayed = null;
		try {
			Date toDay = sdfOld.parse(sdfOld.format(new Date()));
			for(Board board : boards) {
					Date boardDate = board.getCreateDate();
					Date boardDateToCompare=sdfOld.parse(sdfOld.format(boardDate));
					int compare=boardDateToCompare.compareTo(toDay);
					if(compare==0) { //같다 (음수라면 과거)
						dateDisplayed=sdfToDay.format(boardDate);
					}else {
						dateDisplayed=sdfOld.format(boardDate);
					}
					board.setDateDisplayed(dateDisplayed);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		BoardPagenationDTO boardPagenationDTO = BoardPagenationDTO.builder()
				.page(boards).endPage(endPage).startPage(startPage)
				.isNext(isNext).isPrev(isPrev).build();
		return boardPagenationDTO;
	}
	
	@Transactional(readOnly = true)
	public Board 글상세보기(int no) {
		Board board = boardRepository.findById(no).orElseThrow(()->{
			return new IllegalArgumentException("글상세보기 실패: 해당 no를 찾을 수 없습니다. no:"+no);
		});
		return board;
	}
	
	@Transactional
	public int 글수정하기(Board reqBoard, int no) {
		try {
			//영속화 시키기
			Board board = boardRepository.findById(no).orElseThrow(()->{
				return new IllegalArgumentException("글수정하기 실패 해당 번호의 글을 찾을 수 없습니다. no:"+no);
			});
			board.setTitle(reqBoard.getTitle());
			board.setContent(reqBoard.getContent());
			
			List<AttachFile> resultFiles = 첨부파일저장(reqBoard.getAttachFiles(), board);
			//데이터베이스에서 파일 삭제
			for(AttachFile file:reqBoard.getDeleteFiles()){
				attachFileRepository.deleteByUuidAndFileName(file.getUuid(), file.getFileName());
			}
			첨부파일삭제(reqBoard.getDeleteFiles()); //파일 시스템에서 파일 삭제
			return 1;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	@Transactional //첨부파일과 댓글도 모두 삭제하도록 구현해야한다.
	public int 글삭제하기(int no) {
		try {
			List<AttachFile> attahcFiles=attachFileRepository.findWithBoardId(no);
			boardRepository.deleteById(no); //cascade type을 ALL로 해놔서 글 삭제 시 첨부파일 DB도 같이 삭제.
			첨부파일삭제(attahcFiles);
			System.out.println(attahcFiles);
			return 1;
		}catch(Exception e) {
			return 0;
		}
	}
	
	@Transactional
	public void 댓글쓰기(User user, int boardNo, Reply reply) {
		reply.setUser(user);
		Board board = boardRepository.findById(boardNo).orElseThrow(()->{
			return new EntityNotFoundException("댓글쓰기 실패: 해당 no의 게시글을 찾을 수 없습니다. no:"+boardNo);
		});
		reply.setBoard(board);
		replyRepository.save(reply);
	}
	@Transactional(readOnly = true)
	public List<Reply> 댓글보기(int no) {
		List<Reply>replys = replyRepository.findAllByBoardNo(no);
		return replys;
	}
	
	private void 첨부파일삭제(List<AttachFile> attahcFiles) {
		if(attahcFiles == null || attahcFiles.size() == 0) {
			return ;
		}
		log.debug("delete attach files................");
		
		attahcFiles.forEach(attach -> {
			try {
				Path file = Paths.get("C:\\springboot\\upload",attach.getUploadPath(),
						attach.getUuid()+"_"+attach.getFileName());
				Files.deleteIfExists(file);
				//이미지파일 썸네일 지우기는 생략
			}catch(Exception e) {
				log.error("delete file error:"+e.getMessage());
			}
		});
	}
	
	private List<AttachFile> 첨부파일저장(List<AttachFile> attachFiles, Board board){
		for(int i=0; i<attachFiles.size(); i++) {
			AttachFile attachFile = attachFiles.get(i);
			attachFile.setBoard(board);
			AttachFile result = attachFileRepository.save(attachFile);
			attachFiles.get(i).setNo(result.getNo());
		}
		return attachFiles;
	}
	/*
	private void 날짜형식바꾸기(List<Reply> replys) {
		SimpleDateFormat sdfOld = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfToDay = new SimpleDateFormat("HH:mm:ss");
		String dateDisplayed = null;
		try {
			Date toDay = sdfOld.parse(sdfOld.format(new Date()));
			for(Reply reply : replys) {
					Date boardDate = board.getCreateDate();
					Date boardDateToCompare=sdfOld.parse(sdfOld.format(boardDate));
					int compare=boardDateToCompare.compareTo(toDay);
					if(compare==0) { //같다 (음수라면 과거)
						dateDisplayed=sdfToDay.format(boardDate);
					}else {
						dateDisplayed=sdfOld.format(boardDate);
					}
					board.setDateDisplayed(dateDisplayed);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}*/
	
}
