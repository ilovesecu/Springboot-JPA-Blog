package com.pg.iloveblog.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pg.iloveblog.dto.BoardPagenationDTO;
import com.pg.iloveblog.model.AttachFile;
import com.pg.iloveblog.model.Board;
import com.pg.iloveblog.model.User;
import com.pg.iloveblog.repository.AttachFileRepository;
import com.pg.iloveblog.repository.BoardRepository;

import jdk.internal.org.jline.utils.Log;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardService {
	@Resource
	private BoardRepository boardRepository;
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
	public int 글삭제하기(int no) {
		try {
			boardRepository.deleteById(no);
			return 1;
		}catch(Exception e) {
			return 0;
		}
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
	
}
