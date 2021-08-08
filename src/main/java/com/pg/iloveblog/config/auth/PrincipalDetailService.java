package com.pg.iloveblog.config.auth;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pg.iloveblog.model.User;
import com.pg.iloveblog.repository.UserRepository;

//실제로 로그인을 진행하는 메소드
@Service
public class PrincipalDetailService implements UserDetailsService{
	@Resource
	private UserRepository userRepository;
	
	/*
	 * 스프링이 로그인 요청을 가로챌 때 username, password를 가로채는데 
	 * password는 알아서 처리해주지만 username은 DB에 해당 사용자가 실제로있는지 직접 확인해줘야한다.*/
	@Override 
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("userName:"+username);
		User principal = userRepository.findUserId(username).orElseThrow(()->{
			return new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.");
		});
		return new PrincipalDetail(principal); //이때 일어나는게 시큐리티 세션에 유저 정보가 저장된다.
	}
	 
}
