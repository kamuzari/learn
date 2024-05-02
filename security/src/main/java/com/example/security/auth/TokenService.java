package com.example.security.auth;

import static java.text.MessageFormat.*;

import java.text.MessageFormat;
import java.time.Instant;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.security.auth.dto.response.TokenResponseDto;
import com.example.security.exception.model.NotFoundResource;

@Transactional(readOnly = true)
@Service
public class TokenService {
	private final JwtTokenRepository repository;

	public TokenService(JwtTokenRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public void deleteExpiredToken() {
		repository.deleteExpiredToken(LocalDateTime.now());
	}

	public TokenResponseDto getToken(Long userId) {
		JwtToken jwtToken = repository.findById(userId)
			.orElseThrow(
				() -> new NotFoundResource(
					format(
						("사용자  userId -> [{0}] 은 토큰을 발급받지 않거나 혹은 만료된 토큰입니다."),
						userId)
				));
		return new TokenResponseDto(
			jwtToken.userId,
			jwtToken.getToken(),
			jwtToken.getCreatedAt()
		);
	}
}
