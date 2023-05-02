package com.example.oauth.config;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.JdbcOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthenticatedPrincipalOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.oauth.jwt.Jwt;
import com.example.oauth.jwt.JwtAuthenticationFilter;
import com.example.oauth.oauth2.HttpCookieOauth2AuthorizationRequestRepository;
import com.example.oauth.oauth2.Oauth2AuthenticationSuccessHandler;
import com.example.oauth.user.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final JwtConfig jwtConfig;

	private final AuthService authService;

	private final JdbcOperations jdbcOperations;
	private final ClientRegistrationRepository clientRegistrationRepository;

	public WebSecurityConfig(JwtConfig jwtConfig, AuthService authService, JdbcOperations jdbcOperations,
		ClientRegistrationRepository clientRegistrationRepositor) {
		this.jwtConfig = jwtConfig;
		this.authService = authService;
		this.jdbcOperations = jdbcOperations;
		this.clientRegistrationRepository = clientRegistrationRepositor;
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring()
			.antMatchers(HttpMethod.GET, "/assets/**", "/h2-console/**")
			.antMatchers(HttpMethod.POST, "/assets/**", "/h2-console/**")
			.antMatchers(HttpMethod.PATCH, "/assets/**", "/h2-console/**")
			.antMatchers(HttpMethod.DELETE, "/assets/**", "/h2-console/**")
			.antMatchers(HttpMethod.PUT, "/assets/**", "/h2-console/**")
			.requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return (request, response, e) -> {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			Object principal = authentication != null ? authentication.getPrincipal() : null;
			log.warn("{} is denied", principal, e);
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			response.setContentType("text/plain;charset=UTF-8");
			response.getWriter().write("ACCESS DENIED");
			response.getWriter().flush();
			response.getWriter().close();
		};
	}

	@Bean
	public Jwt jwt() {
		return new Jwt(
			jwtConfig.getIssuer(),
			jwtConfig.getClientSecret(),
			jwtConfig.getExpirySeconds()
		);
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter(Jwt jwt) {
		return new JwtAuthenticationFilter(jwtConfig.getHeader(), jwt);
	}

	@Bean
	public Oauth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler(
		Jwt jwt,
		AuthService authService
	) {
		return new Oauth2AuthenticationSuccessHandler(new ObjectMapper(), authService, jwt);
	}

	@Bean
	public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
		return new HttpCookieOauth2AuthorizationRequestRepository();
	}


	@Bean
	public OAuth2AuthorizedClientService authorizedClientService() {
		return new JdbcOAuth2AuthorizedClientService(jdbcOperations, clientRegistrationRepository);
	}

	@Bean
	public OAuth2AuthorizedClientRepository authorizedClientRepository(OAuth2AuthorizedClientService authorizedClientService) {
		return new AuthenticatedPrincipalOAuth2AuthorizedClientRepository(authorizedClientService);
	}

	@Bean
	AuthenticationEntryPoint authenticationEntryPoint() {
		return new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers().authenticated()
			.anyRequest().permitAll()
			.and()
			/**
			 * formLogin, csrf, headers, http-basic, rememberMe, logout filter 비활성화
			 */
			.formLogin()
			.disable()
			.csrf()
			.disable()
			.headers()
			.disable()
			.httpBasic()
			.disable()
			.rememberMe()
			.disable()
			.logout()
			.disable()
			/**
			 * Session 사용하지 않음
			 */
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			/**
			 * 예외처리 핸들러
			 */
			.exceptionHandling()
			.accessDeniedHandler(accessDeniedHandler())
			.and()
			/**
			 * JwtSecurityContextRepository 설정
			 */
			.securityContext()
			.and()
			.oauth2Login()
			.authorizationEndpoint()
			.authorizationRequestRepository(authorizationRequestRepository())
			.and()
			.successHandler(oauth2AuthenticationSuccessHandler(jwt(), authService))
			.authorizedClientRepository(
				authorizedClientRepository(authorizedClientService()))
			.and()
			.addFilterAfter(jwtAuthenticationFilter(jwt()), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

}