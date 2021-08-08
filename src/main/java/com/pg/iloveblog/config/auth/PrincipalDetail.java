package com.pg.iloveblog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.pg.iloveblog.model.User;

//스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetils 타입의 오브젝트를 
//스프링 시큐리티의 고유한 세션 저장소에 저장을 해준다.
public class PrincipalDetail implements UserDetails{
	private User user; //컴포지션
	
	public PrincipalDetail(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		collectors.add(()->{
			return "ROLE_"+user.getRole();//앞에 "ROLE_" 는 시큐리티 규칙이니까 꼭 넣어야한다.
		});
		return collectors;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getId();
	}

	@Override //계정이 만료되지 않았는지 리턴한다. (true:만료안됨)
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override //계정이 잠겨있는지 안잠겨있는지 (true:안잠겨있음)
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override //비밀번호가 만료되지 않았는지 리턴(true:만료안됨)
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override //계정이 활성화(사용가능)인지 리턴(true:활성화)
	public boolean isEnabled() {
		return true;
	}
	
}
