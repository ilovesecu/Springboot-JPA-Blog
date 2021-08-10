package com.pg.iloveblog.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pg.iloveblog.config.auth.PrincipalDetail;
import com.pg.iloveblog.dto.ResponseDTO;
import com.pg.iloveblog.handler.FileHandler;
import com.pg.iloveblog.model.AttachFile;
import com.pg.iloveblog.model.Board;
import com.pg.iloveblog.service.BoardService;

@RestController
@RequestMapping(path="/api")
public class BoardApiController {
	@Resource
	private BoardService boardService;
	@Resource
	private FileHandler fileHandler;
	
	@PostMapping("/board")
	public ResponseDTO<Map<String,Object>> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail princial){
		Map<String,Object> result = new HashMap<>();
		result.put("response","success");
		System.out.println(board);
		Board resultBoard = boardService.글쓰기(board,princial.getUser());
		result.put("result", resultBoard);
		return new ResponseDTO<Map<String,Object>>(HttpStatus.OK.value(), result);
	}
	
	@PostMapping("/file")
	public ResponseDTO<Map<String,Object>> fileHandler(@RequestParam("file") MultipartFile file){
		Map<String,Object> result = new HashMap<>();
		result.put("result","success");
		if(file.isEmpty()) {
			result.put("result","fail");
			result.put("response","file is empty");
			return new ResponseDTO<Map<String,Object>>(HttpStatus.OK.value(),result);
		}
		AttachFile attachFile = fileHandler.parseFileInfo(file);
		System.out.println(attachFile);
		result.put("response",attachFile);
		return new ResponseDTO<Map<String,Object>>(HttpStatus.OK.value(),result);
	}
}
