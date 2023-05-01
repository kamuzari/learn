package com.example.oauth.jwt;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class JwtAuthentication {

	public final String token;

	public final String username;

	JwtAuthentication(String token, String username) {
		this.token = token;
		this.username = username;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("token", token)
			.append("username", username)
			.toString();
	}

}