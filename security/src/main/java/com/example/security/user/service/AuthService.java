package com.example.security.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.security.jwt.JwtAuthenticationToken;
import com.example.security.user.controller.request.JwtAuthentication;
import com.example.security.user.domain.User;
import com.example.security.user.domain.UserRepository;

@Service
public class AuthService implements AuthenticationProvider {
	private static final Logger log = LoggerFactory.getLogger("dasdasd");
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken)authentication;
		String username = String.valueOf(authenticationToken.getPrincipal());
		User user = userRepository.findByUsername(username).orElseThrow(
			() -> new BadCredentialsException("아이디가 일치하지 않습니다."));

		String password = (String)authentication.getCredentials();
		if (!passwordEncoder.matches(password,user.getPassword())) {
			throw new AuthenticationCredentialsNotFoundException("패스워드가 일치하지 않습니다.");
		}
		authenticationToken.setDetails(new JwtAuthentication(user.getId(), user.getUsername()));

		return authenticationToken;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return AuthService.class.isAssignableFrom(authentication);
	}
}
