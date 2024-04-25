package com.example.security.controller;

import static com.example.security.utils.ApiUtils.error;
import static com.example.security.utils.ApiUtils.success;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.jwt.Jwt;
import com.example.security.jwt.JwtAuthenticationToken;
import com.example.security.user.controller.request.AuthRequestDto;
import com.example.security.user.controller.request.JwtAuthentication;
import com.example.security.user.controller.request.LoginResponseDto;
import com.example.security.utils.ApiUtils.ApiResult;

@Validated
@RestController
@RequestMapping("/api/v1/account")
public class LoginController {
	private final Jwt jwt;
	private final AuthenticationProvider authService;

	public LoginController(Jwt jwt, AuthenticationProvider authenticationProvider) {
		this.jwt = jwt;
		this.authService = authenticationProvider;
	}

	@PostMapping(path = "/auth")
	public ResponseEntity<ApiResult> login(@RequestBody AuthRequestDto authRequest) {
		JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(
			authRequest.username(),
			authRequest.password());
		Authentication authenticatedAuth = authService.authenticate(jwtAuthenticationToken);
		JwtAuthentication details = (JwtAuthentication)authenticatedAuth.getDetails();

		try {
			Jwt.Claims claims = Jwt.Claims.of(details.userId(), details.username(), new String[] {"ROLE_USER"});
			String token = jwt.create(claims);
			return new ResponseEntity(success(new LoginResponseDto(token)), new HttpHeaders(), HttpStatus.OK);
		} catch (AuthenticationException e) {
			return new ResponseEntity<>(error(e.getMessage()), HttpStatus.UNAUTHORIZED);
		}
	}
}

