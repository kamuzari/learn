package com.example.security.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.security.jwt.Jwt;
import com.example.security.jwt.JwtAuthenticationToken;
import com.example.security.jwt.JwtTokenConfigure;
import com.example.security.user.controller.request.JwtAuthentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthInterceptor implements HandlerInterceptor {
	private final JwtTokenConfigure configure;
	private final Jwt jwt;

	public AuthInterceptor(JwtTokenConfigure configure, Jwt jwt) {
		this.configure = configure;
		this.jwt = jwt;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
		HttpServletResponse response,
		Object handler) throws Exception {
		// todo: refreshToken
		String token = request.getHeader(configure.getHeader());
		if (token == null || token.isBlank()) {
			throw new JWTVerificationException("헤더가 존재하지 않습니다.");
		}

		Jwt.Claims verifiedClaim = jwt.verify(token);
		JwtAuthentication jwtAuth = new JwtAuthentication(verifiedClaim.getUserId(), verifiedClaim.getUsername());
		JwtAuthenticationToken jwtAuthToken = new JwtAuthenticationToken(jwtAuth, null);
		SecurityContextHolder.getContext().setAuthentication(jwtAuthToken);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
		HttpServletResponse response,
		Object handler,
		ModelAndView modelAndView) throws Exception {
		SecurityContextHolder.clearContext();
	}
}
