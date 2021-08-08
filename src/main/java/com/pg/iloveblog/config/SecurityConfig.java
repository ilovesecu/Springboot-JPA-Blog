package com.pg.iloveblog.config;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.pg.iloveblog.config.auth.PrincipalDetailService;
import com.pg.iloveblog.handler.CustomAuthenticationFailureHandler;

//Spring Security 설정파일
@Configuration
@EnableWebSecurity //시큐리티 필터가 등록이 된다.
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소로 접근하면 권한 및 인증을 미리 체크하겠다.
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Resource
	private PrincipalDetailService principalDetailService;
	@Resource
	private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
	
	@Bean //해당 어노테이션이 붙은 메소드를 스프링이 실행시켜줘서 반환되는 객체를 IoC 컨테이너에 등록을 시켜준다.
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	@Override //시큐리티가 대신 로그인을 해주기 위해서 id와 password를 가로채는데 password가 뭘로 해쉬화 되어있는지 모르니까 그것을 알려주기위함
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//userDetailService라는 실제로 로그인을 진행하는 메소드(직접 구현)에게 패스워드 해쉬함수를 전달해주면된다.
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	@Override // js, css, image 설정은 보안 설정의 영향 밖에 있도록 만들어주는 설정.
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable() //csrf 토큰 비활성화
			.authorizeRequests() //요청이 들어오면
			.antMatchers("/auth/**","/img/**","/","/favicon.ico","/error") //해당 경로라면 
			.permitAll() // 모두 허용
			.anyRequest() //다른 경로들은
			.authenticated() //인증이 필요하다.
			.and()
			.formLogin() //폼 로그인
			.loginPage("/auth/loginForm") //로그인 페이지 설정
			.usernameParameter("userId") //유저아이디 파라미터 이름 설정
			.passwordParameter("userPassword") //비밀번호 파라미터 이름 설정
			.loginProcessingUrl("/auth/loginProc") //스프링 시큐리티가 해당 주소로 오는 로그인을 가로채서 대신 로그인해준다.
			.defaultSuccessUrl("/") //로그인 성공일 시 /로 이동
			.failureHandler(customAuthenticationFailureHandler); //실패 시 핸들러
	}
	
}
