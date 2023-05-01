package com.example.oauth.configures;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

import com.example.oauth.jwt.Jwt;
import com.example.oauth.jwt.JwtAuthenticationFilter;
import com.example.oauth.oauth2.Oauth2AuthenticationSuccessHandler;
import com.example.oauth.user.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigure extends WebSecurityConfigurerAdapter {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final JwtConfigure jwtConfigure;

	public WebSecurityConfigure(JwtConfigure jwtConfigure) {
		this.jwtConfigure = jwtConfigure;
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/assets/**", "/h2-console/**");
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
			jwtConfigure.getIssuer(),
			jwtConfigure.getClientSecret(),
			jwtConfigure.getExpirySeconds()
		);
	}

	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(jwtConfigure.getHeader(), jwt());
	}

	@Bean
	public Oauth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler(Jwt jwt, AuthService authService) {
		return new Oauth2AuthenticationSuccessHandler(new ObjectMapper(), authService, jwt);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
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
			.successHandler(getApplicationContext().getBean(Oauth2AuthenticationSuccessHandler.class))
			.and()
			.addFilterAfter(jwtAuthenticationFilter(), SecurityContextPersistenceFilter.class);
	}

}