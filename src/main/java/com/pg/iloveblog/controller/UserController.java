package com.pg.iloveblog.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.pg.iloveblog.config.auth.PrincipalDetail;
import com.pg.iloveblog.model.KaKaOauthToken;
import com.pg.iloveblog.model.KaKaoProfile;
import com.pg.iloveblog.model.RoleType;
import com.pg.iloveblog.model.User;
import com.pg.iloveblog.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Value("${oauth.key}") //application.yml 에서 주입받는다.
	private String oauthKey;
	
	@GetMapping("/auth/loginForm")
	public String loginForm(@RequestParam(value="error", required = false)String error,
							@RequestParam(value="exception", required = false) String msg,
							@AuthenticationPrincipal PrincipalDetail principalDetail,
							ModelMap modelMap) {
		if(principalDetail != null) return "redirect:/"; //이미 로그인이 되어있는 상태라면 / 로 요청하도록 설정.
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
	
	//Kakao OAuth
	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) {
		//code를 받아왔다면 이제 POST방식으로 엑세스 토큰을 요청한다.
		RestTemplate rt = new RestTemplate();
		//HttpHeader
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		//Body
		MultiValueMap<String, String>params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "14cad6c4dad2c7a829de13df6e114020");
		params.add("redirect_uri", "http://localhost:9490/auth/kakao/callback");
		params.add("code", code);
		//HttpHeader와 HttpBody를 하나의 객체에 담기
		HttpEntity<MultiValueMap<String, String>>kakaoTokenRequest = new HttpEntity<>(params,headers);
		//Request To KaKao
		ResponseEntity<String>tokenResponse=rt.exchange("https://kauth.kakao.com/oauth/token", //요청주소 
														HttpMethod.POST, //요청방식
														kakaoTokenRequest, //요청헤더, 요청바디
														String.class //응답받을 타입
		);
		//JSON → 자바 객체로 담을 때 라이브러리는 Gson, JsonSimple, ObjectMapper 등 많은 라이브러리가 있다.
		ObjectMapper objectMapper = new ObjectMapper();
		KaKaOauthToken kaKaOauthToken = null;
		try {
			//response.getBody() → OAuthToken.class 로 파싱
			kaKaOauthToken = objectMapper.readValue(tokenResponse.getBody(), KaKaOauthToken.class);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//토큰으로 자원서버 접근하기
		RestTemplate getKaKaoResource = new RestTemplate();
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+kaKaOauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		HttpEntity<MultiValueMap<String, String>>kakaoResrouceRequest = new HttpEntity<>(headers2);
		ResponseEntity<String> kakaoResource 
					= getKaKaoResource.exchange("https://kapi.kakao.com/v2/user/me",
							HttpMethod.POST,
							kakaoResrouceRequest,
							String.class);
		objectMapper = new ObjectMapper();
		objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
		KaKaoProfile kaKaoProfile = null;
		try {
			//kakaoResource → KaKaoPrifle.class로 파싱
			kaKaoProfile = objectMapper.readValue(kakaoResource.getBody(), KaKaoProfile.class);
		}catch(Exception e) {
			e.printStackTrace();
		}
		kakaoLogin(kaKaoProfile); //카카오 로그인 실행
		return "redirect:/";
	}
	
	//카카오 로그인 실행
	private void kakaoLogin(KaKaoProfile kaKaoProfile) {
		//가입자인지 비가입자인지 체크
		User kakaoUser = User.builder()
				.id(kaKaoProfile.getKakaoAccount().getEmail()+"_"+kaKaoProfile.getId())
				.name(kaKaoProfile.getProperties().getNickname())
				.email(kaKaoProfile.getKakaoAccount().getEmail())
				.password(oauthKey)
				.oldPassword(oauthKey)
				.role(RoleType.USER)
				.oauth("kakao").build();
		User originUser = userService.아아디로회원찾기(kakaoUser.getId());
		if(originUser.getId() == null) {
			userService.회원가입(kakaoUser);
		}
		semiAutoLoginProc(kakaoUser,oauthKey); //OAuth 로그인 처리
	}
	
	//OAuth 로그인처리
	private void semiAutoLoginProc(User user,String passwords) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getId(), oauthKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
