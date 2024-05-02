package com.example.security.auth.dto.response;

import java.time.LocalDateTime;

public record TokenResponseDto(
	Long userId,
	String token,
	LocalDateTime createdAt
) {
}
