package com.pg.iloveblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
	@GetMapping("/auth/loginForm")
	public String loginForm(@RequestParam(value="error", required = false)String error,
							@RequestParam(value="exception", required = false) String msg, 
							ModelMap modelMap) {
		System.out.println(error);
		System.out.println(msg);
		return "user/loginForm";
	}
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
}
