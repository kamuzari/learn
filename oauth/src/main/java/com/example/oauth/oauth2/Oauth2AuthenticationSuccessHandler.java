package com.example.oauth.oauth2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.example.oauth.jwt.Jwt;
import com.example.oauth.user.domain.entity.UserEntity;
import com.example.oauth.user.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Oauth2AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	// 인증 완료 됬을 때 처리 핸들러

	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
	private final ObjectMapper objectMapper;
	private final AuthService authService;
	private final Jwt jwt;

	public Oauth2AuthenticationSuccessHandler(ObjectMapper objectMapper, AuthService authService, Jwt jwt) {
		this.objectMapper = objectMapper;
		this.authService = authService;
		this.jwt = jwt;
	}

	@Override
	public void onAuthenticationSuccess(
		HttpServletRequest request,
		HttpServletResponse response,
		Authentication authentication
	) throws ServletException, IOException {
		Object principal = authentication.getPrincipal();
		log.info("authentication success @@@ -- principal : {}", principal);

		if (authentication instanceof OAuth2AuthenticationToken) {
			OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken)authentication;
			OAuth2User oauthPrincipal = oauthToken.getPrincipal();
			String registrationId = oauthToken.getAuthorizedClientRegistrationId();

			UserEntity user = processUserOAuth2UserJoin(oauthPrincipal, registrationId);
			String loginSuccessJson = generateLoginSuccessJson(user);
			response.setContentType("application/json;charset=UTF-8");
			response.setContentLength(loginSuccessJson.getBytes(StandardCharsets.UTF_8).length);
			response.getWriter().write(loginSuccessJson);
			return;
		}

		super.onAuthenticationSuccess(request, response, authentication);
	}

	private String generateLoginSuccessJson(UserEntity user) {
		String token = generateToken(user);
		return "{\"token\":\"" + token + "\","
			+ " \"username\":\"" + user.getUsername()
			+ "\", \"group\":\"" + user.getGroup() + "\"}";
	}

	private UserEntity processUserOAuth2UserJoin(OAuth2User oAuth2User, String registrationId) {
		return authService.join(oAuth2User, registrationId);
	}

	private String generateToken(UserEntity user) {
		return jwt.sign(Jwt.Claims.from(user.getUsername(), new String[] {"ROLE_USER"}));
	}
}
