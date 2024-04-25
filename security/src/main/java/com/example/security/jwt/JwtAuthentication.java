package com.example.security.jwt;

import lombok.Data;

public class JwtAuthentication {
	private Long userId;
	private Long username;

	public JwtAuthentication(Long userId, Long username) {
		this.userId = userId;
		this.username = username;
	}

	public Long getUserId() {
		return userId;
	}

	public Long getUsername() {
		return username;
	}
}
