package com.example.security.user.controller.response;

public record TokenDto(
	String headerName,
	String token
) {

}
