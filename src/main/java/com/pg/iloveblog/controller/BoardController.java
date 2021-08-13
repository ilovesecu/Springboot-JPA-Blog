package com.pg.iloveblog.controller;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.pg.iloveblog.dto.BoardPagenationDTO;
import com.pg.iloveblog.model.Board;
import com.pg.iloveblog.service.BoardService;

@Controller
public class BoardController {
	@Resource
	BoardService boardService;
	
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
	
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
}
