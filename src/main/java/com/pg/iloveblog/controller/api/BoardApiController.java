package com.pg.iloveblog.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
import com.pg.iloveblog.model.Reply;
import com.pg.iloveblog.service.BoardService;

import jdk.internal.org.jline.utils.Log;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
		Board resultBoard = boardService.글쓰기(board,princial.getUser());
		result.put("result", resultBoard);
		return new ResponseDTO<Map<String,Object>>(HttpStatus.OK.value(), result);
	}
	@PutMapping("/board/{no}")
	public ResponseDTO<Integer> update(@RequestBody Board board, @PathVariable int no){
		System.out.println(board);
		int result = boardService.글수정하기(board,no);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),result);
	}
	
	@DeleteMapping("/board/{no}")
	public ResponseDTO<Integer> deleteBoardByNo(@PathVariable int no){
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),boardService.글삭제하기(no));
	}
	
	@PostMapping("/board/{no}/reply") //댓글 작성
	public ResponseDTO<Integer> replySave(@PathVariable int no,
										  @RequestBody Reply reply,
										  @AuthenticationPrincipal PrincipalDetail principal){
		boardService.댓글쓰기(principal.getUser(),no,reply);
		return new ResponseDTO<Integer>(HttpStatus.OK.value(),1);
	}
	
	@GetMapping("/board/{no}/reply") //댓글 조회
	public ResponseDTO<Map<String,Object>> replySave(@PathVariable int no){
		Map<String,Object> result = new HashMap<>();
		List<Reply>replys=boardService.댓글보기(no);
		result.put("data",replys);
		return new ResponseDTO<Map<String,Object>>(HttpStatus.OK.value(),result);
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
