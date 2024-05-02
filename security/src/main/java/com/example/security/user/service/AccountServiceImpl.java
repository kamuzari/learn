package com.example.security.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.security.auth.JwtToken;
import com.example.security.auth.JwtTokenRepository;
import com.example.security.jwt.Jwt;
import com.example.security.jwt.JwtAuthenticationToken;
import com.example.security.jwt.JwtTokenConfigure;
import com.example.security.user.controller.request.JwtAuthenticationDto;
import com.example.security.user.controller.response.LoginResponseDto;
import com.example.security.user.controller.response.TokenDto;
import com.example.security.user.domain.User;
import com.example.security.user.domain.UserRepository;

@Service
public class AccountServiceImpl implements AccountService {
	private static final Logger log = LoggerFactory.getLogger(AnnotationConfigUtils.class);
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final Jwt jwt;
	private final JwtTokenConfigure jwtTokenConfigure;
	private final JwtTokenRepository jwtTokenRepository;

	public AccountServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, Jwt jwt,
		JwtTokenConfigure jwtTokenConfigure,
		JwtTokenRepository jwtTokenRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwt = jwt;
		this.jwtTokenConfigure = jwtTokenConfigure;
		this.jwtTokenRepository = jwtTokenRepository;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken)authentication;
		String username = String.valueOf(authenticationToken.getPrincipal());
		User user = userRepository.findByUsername(username).orElseThrow(
			() -> new BadCredentialsException("아이디가 일치하지 않습니다."));

		String password = (String)authentication.getCredentials();
		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new AuthenticationCredentialsNotFoundException("패스워드가 일치하지 않습니다.");
		}
		authenticationToken.setDetails(new JwtAuthenticationDto(user.getId(), user.getUsername()));

		return authenticationToken;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return AccountServiceImpl.class.isAssignableFrom(authentication);
	}

	@Override
	@Transactional
	public LoginResponseDto createTokens(JwtAuthenticationDto authenticationDto) {
		var accessClaims = Jwt.Claims.of(
			authenticationDto.userId(),
			authenticationDto.username(),
			new String[] {"ROLE_USER"});
		var refreshClaims = Jwt.Claims.of(authenticationDto.userId());

		String accessToken = null;
		String refreshToken = null;
		accessToken = jwt.createForAccess(accessClaims);
		refreshToken = jwt.createForRefresh(refreshClaims);
		jwtTokenRepository.save(new JwtToken(authenticationDto.userId(), refreshToken));

		return new LoginResponseDto(
			new TokenDto(jwtTokenConfigure.accessHeader(), accessToken),
			new TokenDto(jwtTokenConfigure.refreshHeader(), refreshToken)
		);
	}

}
