package com.example.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.security.jwt.Jwt;
import com.example.security.jwt.JwtTokenConfigure;

@SpringBootTest
public class JwtTest {

	@Autowired
	Jwt jwt;

	@Autowired
	JwtTokenConfigure jwtTokenConfigure;

	@DisplayName("token 생성하기")
	@Test
	void testJwtClaim() {
		//given
		Jwt.Claims givenClaims = Jwt.Claims.of(1L, "testuser@naver.com", new String[] {"ROLE_GUEST"});
		String token = jwt.create(givenClaims);
		//when
		Jwt.Claims verifiedClaim = jwt.verify(token);
		//then
		assertThat(jwt).isNotNull();
		assertThat(token).isNotNull();
		assertThat(givenClaims.getUsername()).isEqualTo(verifiedClaim.getUsername());
		assertThat(givenClaims.getUserId()).isEqualTo(verifiedClaim.getUserId());
		assertThat(givenClaims.getRoles()).isEqualTo(verifiedClaim.getRoles());
	}

	@DisplayName("잘못된 토큰은 JWTDecodeExeption 발생한다.")
	@Test
	void failNotVerifiedToken() {
		//given
		String token = "nsajkdnjkansdjnaksndjkasd";
		//when
		//then
		Assertions.assertThatThrownBy(() -> jwt.verify(token))
			.isInstanceOf(JWTDecodeException.class);
	}

	@DisplayName("config 프로퍼티 로딩 테스트")
	@Test
	void testSecurityProperty() {
		//given
		//when
		//then
		assertThat(jwtTokenConfigure).isNotNull();
		assertThat(jwtTokenConfigure.getClientSecret()).isNotNull();
		assertThat(jwtTokenConfigure.getHeader()).isNotNull();
		assertThat(jwtTokenConfigure.getExpirySeconds()).isNotNull();
		assertThat(jwtTokenConfigure.getIssuer()).isNotNull();
	}

	@DisplayName("Jwt 토큰 만료시 TokenExpiredException 이 발생한다.")
	@Test
	void testTokenExpired() throws InterruptedException {
		//given
		Jwt.Claims givenClaims = Jwt.Claims.of(1L, "testuser@naver.com", new String[] {"ROLE_GUEST"});
		String token = jwt.create(givenClaims);
		//when
		Thread.sleep(jwtTokenConfigure.getExpirySeconds() + 2000L);
		//then
		assertThatThrownBy(()->jwt.verify(token))
			.isInstanceOf(TokenExpiredException.class);
	}
}
