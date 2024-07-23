package com.example.security.user.service;

import org.springframework.security.authentication.AuthenticationProvider;

import com.example.security.user.controller.request.JwtAuthenticationDto;
import com.example.security.user.controller.response.LoginResponseDto;

public interface AccountService extends AuthenticationProvider {
	LoginResponseDto createTokens(JwtAuthenticationDto authenticationDto);
}
