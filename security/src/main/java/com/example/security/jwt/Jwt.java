package com.example.security.jwt;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.EqualsAndHashCode;
import lombok.Getter;

public final class Jwt {

	private final String issuer;

	private final String clientSecret;

	private final int expirySeconds;

	private final Algorithm algorithm;

	private final JWTVerifier jwtVerifier;

	public Jwt(String issuer, String clientSecret, int expirySeconds) {
		this.issuer = issuer;
		this.clientSecret = clientSecret;
		this.expirySeconds = expirySeconds;
		this.algorithm = Algorithm.HMAC512(clientSecret);
		this.jwtVerifier = JWT.require(algorithm)
			.withIssuer(issuer)
			.build();
	}

	public String create(Claims claims) {
		Date now = new Date();
		JWTCreator.Builder builder = JWT.create();
		builder.withIssuer(issuer);
		builder.withIssuedAt(now);
		if (expirySeconds > 0) {
			builder.withExpiresAt(new Date(now.getTime() + expirySeconds * 1_000L));
		}
		builder.withClaim("userId", claims.userId);
		builder.withClaim("username", claims.username);
		builder.withArrayClaim("roles", claims.roles);
		return builder.sign(algorithm);
	}

	public Claims verify(String token) throws JWTVerificationException {
		return new Claims(jwtVerifier.verify(token));
	}

	public String getIssuer() {
		return issuer;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public int getExpirySeconds() {
		return expirySeconds;
	}

	public Algorithm getAlgorithm() {
		return algorithm;
	}

	public JWTVerifier getJwtVerifier() {
		return jwtVerifier;
	}

	@Getter
	@EqualsAndHashCode
	static public class Claims {
		Long userId;
		String username;
		String[] roles;
		Date issuedAt;
		Date expiredAt;

		private Claims() {/*empty*/}

		Claims(DecodedJWT decodedJWT) {
			Claim userId = decodedJWT.getClaim("userId");
			if (!userId.isNull()) {
				this.userId = userId.asLong();
			}
			Claim username = decodedJWT.getClaim("username");
			if (!username.isNull()) {
				this.username = username.asString();
			}

			Claim roles = decodedJWT.getClaim("roles");
			if (!roles.isNull()) {
				this.roles = roles.asArray(String.class);
			}
			this.issuedAt = decodedJWT.getIssuedAt();
			this.expiredAt = decodedJWT.getExpiresAt();
		}

		public static Claims of(long userId, String username, String[] roles) {
			Claims claims = new Claims();
			claims.userId = userId;
			claims.username = username;
			claims.roles = roles;
			return claims;
		}

		long getIssuedAt() {
			return issuedAt != null ? issuedAt.getTime() : -1;
		}

		long getExpiration() {
			return expiredAt != null ? expiredAt.getTime() : -1;
		}

		void eraseIssuedAt() {
			issuedAt = null;
		}

		void eraseExpiration() {
			expiredAt = null;
		}
	}

}
