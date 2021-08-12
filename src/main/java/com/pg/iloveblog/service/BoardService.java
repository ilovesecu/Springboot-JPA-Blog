package com.pg.iloveblog.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pg.iloveblog.model.AttachFile;
import com.pg.iloveblog.model.Board;
import com.pg.iloveblog.model.User;
import com.pg.iloveblog.repository.AttachFileRepository;
import com.pg.iloveblog.repository.BoardRepository;

@Service
public class BoardService {
	@Resource
	private BoardRepository boardRepository;
	@Resource
	private AttachFileRepository attachFileRepository;
	
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
	public List<Board> 글목록(){
		//return boardRepository.findAllPost();
		List<Board>boards=boardRepository.findAll();
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
		return boards;
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
