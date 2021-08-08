package com.pg.iloveblog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//Spring Security 설정파일
@Configuration
@EnableWebSecurity //시큐리티 필터가 등록이 된다.
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소로 접근하면 권한 및 인증을 미리 체크하겠다.
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Bean //해당 어노테이션이 붙은 메소드를 스프링이 실행시켜줘서 반환되는 객체를 IoC 컨테이너에 등록을 시켜준다.
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable() //csrf 토큰 비활성화
			.authorizeRequests() //요청이 들어오면
			.antMatchers("/auth/**","/js/**","/css/**","/image/**","/") //해당 경로라면 
			.permitAll() // 모두 허용
			.anyRequest() //다른 경로들은
			.authenticated() //인증이 필요하다.
			.and()
			.formLogin() //폼 로그인
			.loginPage("/auth/loginForm") //로그인 페이지 설정
			.usernameParameter("userId") //유저아이디 파라미터 이름 설정
			.passwordParameter("userPassword"); //비밀번호 파라미터 이름 설정
	}
	
}
