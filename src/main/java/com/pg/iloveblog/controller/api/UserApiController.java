package com.pg.iloveblog.controller.api;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	@Resource
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/auth/joinProc")
	public ResponseDTO<Map<String,Object>> save(@RequestBody User reqUser){
		Map<String,Object> result = new HashMap<String,Object>();
		boolean exceptionCheckResult = exceptionJoin(reqUser,result);
		if(!exceptionCheckResult) {
			result.put("response","fail");
			return new ResponseDTO<Map<String,Object>>(HttpStatus.OK.value(),result);
		}
		result.put("response","success");
		reqUser.setRole(RoleType.USER);
		result.put("result",userService.회원가입(reqUser));
		return new ResponseDTO<Map<String,Object>>(HttpStatus.OK.value(),result);
	}
	
	@PutMapping("/user")
	public ResponseDTO<Map<String,Object>> update(@RequestBody User reqUser){
		Map<String,Object> result = new HashMap<String,Object>();
		boolean exceptionCheckResult = exceptionUpdate(reqUser,result); //예외처리 진행
		if(!exceptionCheckResult) {
			result.put("response","fail");
			return new ResponseDTO<Map<String,Object>>(HttpStatus.OK.value(),result);
		}
		//회원수정 진행
		ResponseDTO<Map<String,Object>> res = userService.회원수정(reqUser,result);
		//회원 수정을 한 다음에는 로그인을 다시 해줘야 Pricipal이 refresh돼서 화면에서도 변경이된다.
		if(res.getStatus() == 200)semiAutoLoginProc(reqUser); 
		return res;
	}
	
	private void semiAutoLoginProc(User user) {
		String password = "";
		if(user.getPassword()!=null && !user.getPassword().equals("")) password = user.getPassword();
		else password = user.getOldPassword();
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getId(), password));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	//회원가입 빈칸 예외처리
	private boolean exceptionJoin(User reqUser, Map<String,Object> result) {
		if(reqUser.getPassword()==null || reqUser.getPassword().equals("")) {
			result.put("error", "비밀번호를 입력해주세요");
			return false;
		}
		return exceptionCheck(reqUser, result);
	}
	//회원수정 빈칸 예외처리
	private boolean exceptionUpdate(User reqUser, Map<String,Object> result) {
		if(reqUser.getOldPassword()==null || reqUser.getOldPassword().equals("")) {
			result.put("error", "기존 비밀번호를 입력해주세요");
			return false;
		}
		return exceptionCheck(reqUser, result);
	}
	
	//공통 빈칸 예외처리
	private boolean exceptionCheck(User reqUser,Map<String,Object> result) {
		if(reqUser.getId()==null || reqUser.getId().equals("")) {
			result.put("error", "아이디를 입력해주세요");
			return false;
		}
		if(reqUser.getName()==null || reqUser.getName().equals("")) {
			result.put("error", "이름을 입력해주세요");
			return false;
		}
		if(reqUser.getEmail()==null || reqUser.getEmail().equals("")) {
			result.put("error", "이메일을 입력해주세요");
			return false;
		}
		return true;
	}
}
