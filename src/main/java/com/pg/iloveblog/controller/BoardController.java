package com.pg.iloveblog.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.pg.iloveblog.model.Board;
import com.pg.iloveblog.repository.BoardRepository;
import com.pg.iloveblog.service.BoardService;

@Controller
public class BoardController {
	@Resource
	BoardService boardService;
	
	@GetMapping({"/",""})
	public String index(ModelMap modelMap) {
		List<Board>boards=boardService.글목록();
		modelMap.addAttribute("boards", boards);
		return "index";
	}
	
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
}
