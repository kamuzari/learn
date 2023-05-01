package com.example.oauth.user.web.dto.response;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public record UserLoginResponse (String token, String username,String group){

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("token", token)
			.append("username", username)
			.append("group", group)
			.toString();
	}
}