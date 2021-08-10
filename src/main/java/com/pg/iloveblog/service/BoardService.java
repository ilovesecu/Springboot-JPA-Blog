package com.pg.iloveblog.service;

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
