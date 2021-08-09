package com.pg.iloveblog.controller.api;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pg.iloveblog.config.auth.PrincipalDetail;
import com.pg.iloveblog.dto.ResponseDTO;
import com.pg.iloveblog.model.Board;
import com.pg.iloveblog.service.BoardService;

@RestController
@RequestMapping(path="/api")
public class BoardApiController {
	@Resource
	private BoardService boardService;
	
	@PostMapping("/board")
	public ResponseDTO<Map<String,Object>> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail princial){
		System.out.println("asd");
		Map<String,Object> result = new HashMap<>();
		result.put("response","success");
		Board resultBoard = boardService.글쓰기(board,princial.getUser());
		result.put("result", resultBoard);
		return new ResponseDTO<Map<String,Object>>(HttpStatus.OK.value(), result);
	}
}
