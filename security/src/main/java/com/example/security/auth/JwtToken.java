package com.example.security.auth;

import java.sql.Blob;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class JwtToken {
	@Id
	Long userId;

	@Column(columnDefinition = "clob")
	private String token;

	public JwtToken(Long userId, String token) {
		this.userId = userId;
		this.token = token;
	}
}
