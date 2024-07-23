package com.example.security.config.interceptor;

import static java.text.MessageFormat.*;

import java.time.Instant;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.security.auth.JwtToken;
import com.example.security.auth.JwtTokenRepository;
import com.example.security.auth.TokenService;
import com.example.security.auth.dto.response.TokenResponseDto;
import com.example.security.exception.model.NotFoundResource;
import com.example.security.exception.model.ReLoginException;
import com.example.security.jwt.Jwt;
import com.example.security.jwt.JwtAuthenticationToken;
import com.example.security.jwt.JwtTokenConfigure;
import com.example.security.user.controller.request.JwtAuthenticationDto;
import com.example.security.user.domain.User;
import com.example.security.user.domain.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {
	private static final Logger log = LoggerFactory.getLogger(AuthInterceptor.class);
	private final JwtTokenConfigure jwtConfig;
	private final TokenService tokenService;
	private final UserRepository userRepository;
	private final Jwt jwt;

	public AuthInterceptor(JwtTokenConfigure jwtConfig, TokenService tokenService, UserRepository userRepository,
		Jwt jwt) {
		this.jwtConfig = jwtConfig;
		this.tokenService = tokenService;
		this.userRepository = userRepository;
		this.jwt = jwt;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
		HttpServletResponse response,
		Object handler) throws Exception {
		String accessToken = request.getHeader(jwtConfig.accessHeader());
		String refreshToken = request.getHeader(jwtConfig.refreshHeader());

		boolean isExistHeader = accessToken != null && refreshToken != null;
		if (!isExistHeader) {
			throw new BadCredentialsException("token 정보가 올바르지 않습니다.");
		}

		boolean isBlankHeader = accessToken.isBlank() || refreshToken.isBlank();
		if (isBlankHeader) {
			throw new BadCredentialsException("token 정보가 올바르지 않습니다.");
		}

		Jwt.Claims verifiedClaim = null;
		try {
			verifiedClaim = jwt.verify(accessToken);
		} catch (TokenExpiredException e) {
			log.info("access token expired! {} progress verify refresh...", e.getMessage());
			reIssueAccessToken(response, refreshToken);
			return true;
		}

		JwtAuthenticationDto jwtAuth = new JwtAuthenticationDto(verifiedClaim.getUserId(), verifiedClaim.getUsername());
		JwtAuthenticationToken jwtAuthToken = new JwtAuthenticationToken(jwtAuth, null);
		SecurityContextHolder.getContext().setAuthentication(jwtAuthToken);
		addHeader(response, accessToken, refreshToken);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
		HttpServletResponse response,
		Object handler,
		ModelAndView modelAndView) {
		SecurityContextHolder.clearContext();
	}

	private void reIssueAccessToken(HttpServletResponse response, String refreshToken) {

		try {
			TokenResponseDto jwtRefreshToken = verifyRefreshToken(refreshToken);
			User user = userRepository.findById(jwtRefreshToken.userId())
				.orElseThrow(() -> new NotFoundResource(
					format("존재하지 않는 리소스입니다. [userid -> {0}]", jwtRefreshToken.userId()))
				);
			Jwt.Claims accessClaim = Jwt.Claims.of(user.getId(), user.getUsername(), new String[] {"ROLE_USER"});
			String newAccessToken = jwt.createForAccess(accessClaim);
			addHeader(response, newAccessToken, refreshToken);
		} catch (TokenExpiredException | NotFoundResource e) {
			tokenService.deleteExpiredToken();
			throw new ReLoginException(
				"refresh token이 만료되어 재인증이 필요합니다.",
				"다시 로그인 해주세요."
			);
		}
	}

	private TokenResponseDto verifyRefreshToken(String refreshToken) {
		Jwt.Claims verifiedRefreshClaim = jwt.verify(refreshToken);

		TokenResponseDto jwtRefreshToken = null;
		try {
			jwtRefreshToken = tokenService.getToken(verifiedRefreshClaim.getUserId());
		} catch (NotFoundResource e) {
			throw new TokenExpiredException("refresh token 만료되었습니다.", Instant.now());
		}

		if (!jwtRefreshToken.token().equals(refreshToken)) {
			throw new BadCredentialsException("[refresh token] 내부적으로 발급한 토큰과 사용자에게 보낸 토큰이 다릅니다.");
		}

		return jwtRefreshToken;
	}

	private void addHeader(HttpServletResponse response, String accessToken, String refreshToken) {
		response.addHeader(jwtConfig.accessHeader(), accessToken);
		response.addHeader(jwtConfig.refreshHeader(), refreshToken);
	}
}
