package com.pg.iloveblog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pg.iloveblog.config.auth.PrincipalDetail;

@Controller
public class UserController {
	@GetMapping("/auth/loginForm")
	public String loginForm(@RequestParam(value="error", required = false)String error,
							@RequestParam(value="exception", required = false) String msg, 
							ModelMap modelMap) {
		modelMap.addAttribute("error",error);
		modelMap.addAttribute("msg",msg);
		return "user/loginForm";
	}
	
	@GetMapping("/user/updateForm")
	public String update(@AuthenticationPrincipal PrincipalDetail principal, ModelMap modelMap) {
		modelMap.addAttribute("principal", principal);
		return "user/updateForm";
	}
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
}
