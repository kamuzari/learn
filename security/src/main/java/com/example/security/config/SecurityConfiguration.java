package com.example.security.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.security.jwt.Jwt;
import com.example.security.jwt.JwtTokenConfigure;

@EnableConfigurationProperties({JwtTokenConfigure.class})
@Configuration
public class SecurityConfiguration {

	private final JwtTokenConfigure jwtTokenConfigure;

	public SecurityConfiguration(JwtTokenConfigure jwtTokenConfigure) {
		this.jwtTokenConfigure = jwtTokenConfigure;
	}

	@Bean
	public Jwt jwt() {
		return new Jwt(jwtTokenConfigure.issuer(),
			jwtTokenConfigure.clientSecret(),
			jwtTokenConfigure.accessExpirySeconds(),
			jwtTokenConfigure.refreshExpirySeconds()
		);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.
			logout(logoutConfig -> logoutConfig.disable())
			.formLogin(formLoginConfig -> formLoginConfig.disable())
			.sessionManagement(sessionLoginConfig -> sessionLoginConfig.disable())
			.oidcLogout(OidLoginConfig -> OidLoginConfig.disable())
			.csrf(csrfConfig -> csrfConfig.disable())
			.authorizeHttpRequests(
				authRequest -> authRequest.requestMatchers(PathRequest.toH2Console()).permitAll()
					.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
					.requestMatchers(HttpMethod.POST, "/api/v1/account/**").permitAll()
					.anyRequest().authenticated()
			)
			.build();
	}
}

