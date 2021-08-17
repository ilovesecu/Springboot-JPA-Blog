package com.pg.iloveblog.controller;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.pg.iloveblog.dto.BoardPagenationDTO;
import com.pg.iloveblog.model.Board;
import com.pg.iloveblog.service.BoardService;

@Controller
public class BoardController {
	@Resource
	BoardService boardService;
	
	//게시글 목록보기
	@GetMapping({"/",""})
	public String index(ModelMap modelMap, 
				@PageableDefault(size=2, sort="no", direction = Sort.Direction.DESC)Pageable pageable ) {
		BoardPagenationDTO boards=boardService.글목록(pageable);
		
		modelMap.addAttribute("boards", boards.getPage());
		modelMap.addAttribute("endPage",boards.getEndPage());
		modelMap.addAttribute("startPage",boards.getStartPage());
		modelMap.addAttribute("isPrev",boards.isPrev());
		modelMap.addAttribute("isNext",boards.isNext());
		return "index";
	}
	
	//게시글 상세보기
	@GetMapping("/board/{no}")
	public String detail(@PathVariable int no,ModelMap modelMap) {
		modelMap.addAttribute("board",boardService.글상세보기(no));
		return "board/detail";
	}
	
	//게시글 수정하기
	@GetMapping("/board/{no}/updateForm")
	public String update(@PathVariable int no, ModelMap modelMap) {
		modelMap.addAttribute("board",boardService.글상세보기(no));
		return "board/updateForm";
	}
	
	//게시글 저장하기
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
}
