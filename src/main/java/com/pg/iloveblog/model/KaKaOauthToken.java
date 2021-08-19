package com.pg.iloveblog.model;

import lombok.Data;

//카카오 OAuth 토큰 모델
@Data
public class KaKaOauthToken {
	private String access_token;
	private String token_type;
	private String refresh_token;
	private String expires_in;
	private String scope;
	private String refresh_token_expires_in;
}
