package com.example.security.user.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.security.jwt.JwtAuthenticationToken;
import com.example.security.user.controller.request.LoginRequestDto;
import com.example.security.user.controller.request.JwtAuthenticationDto;
import com.example.security.user.controller.response.LoginResponseDto;
import com.example.security.user.service.AccountService;
import com.example.security.utils.ApiUtils;
import com.example.security.utils.ApiUtils.ApiResult;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {
	private final AccountService accountService;

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping(path = "/auth")
	public ResponseEntity<ApiResult> login(@Validated @RequestBody LoginRequestDto authRequest) {
		var jwtAuthenticationToken = new JwtAuthenticationToken(authRequest.username(), authRequest.password());
		Authentication authenticatedAuth = accountService.authenticate(jwtAuthenticationToken);
		JwtAuthenticationDto details = (JwtAuthenticationDto)authenticatedAuth.getDetails();
		LoginResponseDto responseDto = accountService.createTokens(details);

		return new ResponseEntity(ApiUtils.success(responseDto), new HttpHeaders(), HttpStatus.OK);
	}

}

