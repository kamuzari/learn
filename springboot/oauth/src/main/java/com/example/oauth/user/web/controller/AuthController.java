package com.example.oauth.user.web.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.oauth.jwt.JwtAuthentication;
import com.example.oauth.user.service.AuthService;
import com.example.oauth.user.web.dto.response.UserLoginResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@GetMapping(path = "/user/me")
	public UserLoginResponse me(@AuthenticationPrincipal JwtAuthentication authentication) {
		return authService.findByUsername(authentication.username)
			.map(user ->
				new UserLoginResponse(authentication.token, authentication.username, user.getGroup())
			)
			.orElseThrow(() -> new IllegalArgumentException("Could not found user for " + authentication.username));
	}
}