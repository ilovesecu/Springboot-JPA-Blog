package com.pg.iloveblog.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pg.iloveblog.model.Board;
import com.pg.iloveblog.model.User;
import com.pg.iloveblog.repository.BoardRepository;

@Service
public class BoardService {
	@Resource
	private BoardRepository boardRepository;
	
	@Transactional
	public Board 글쓰기(Board board, User user) {
		board.setCount(0);
		board.setUser(user);
		Board result = boardRepository.save(board);
		return result;
	}
}
