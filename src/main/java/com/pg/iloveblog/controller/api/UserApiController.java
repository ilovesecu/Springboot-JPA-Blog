package com.pg.iloveblog.controller.api;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pg.iloveblog.dto.ResponseDTO;
import com.pg.iloveblog.model.RoleType;
import com.pg.iloveblog.model.User;
import com.pg.iloveblog.service.UserService;

@RestController
public class UserApiController {
	@Resource
	private UserService userService;
	
	@PostMapping("/auth/joinProc")
	public ResponseDTO<Map<String,Object>> save(@RequestBody User reqUser){
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("response","success");
		reqUser.setRole(RoleType.USER);
		result.put("result",userService.회원가입(reqUser));
		return new ResponseDTO<Map<String,Object>>(HttpStatus.OK.value(),result);
	}
}
