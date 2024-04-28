package com.example.security.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;

@SpringBootTest
public class JwtTest {

	@Autowired
	Jwt jwt;

	@Autowired
	JwtTokenConfigure jwtTokenConfigure;

	@DisplayName("access token 생성하기")
	@Test
	void testCreateAccessToken() {
		//given
		Jwt.Claims givenClaims = Jwt.Claims.of(1L, "testuser@naver.com", new String[] {"ROLE_GUEST"});
		String token = jwt.createForAccess(givenClaims);
		//when
		Jwt.Claims verifiedClaim = jwt.verify(token);
		//then
		assertThat(jwt).isNotNull();
		assertThat(token).isNotNull();
		assertThat(givenClaims.getUsername()).isEqualTo(verifiedClaim.getUsername());
		assertThat(givenClaims.getUserId()).isEqualTo(verifiedClaim.getUserId());
		assertThat(givenClaims.getRoles()).isEqualTo(verifiedClaim.getRoles());
	}

	@DisplayName("refresh token 생성하기")
	@Test
	void testCreateRefreshToken() {
		//given
		Jwt.Claims givenClaims = Jwt.Claims.of(1L);
		String token = jwt.createForRefresh(givenClaims);
		//when
		Jwt.Claims verifiedClaim = jwt.verify(token);
		//then
		assertThat(jwt).isNotNull();
		assertThat(token).isNotNull();
		assertThat(givenClaims.getUserId()).isEqualTo(verifiedClaim.getUserId());
		assertThat(givenClaims.getUsername()).isNull();
		assertThat(givenClaims.getRoles()).isNull();
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
		assertThat(jwtTokenConfigure.clientSecret()).isNotNull();
		assertThat(jwtTokenConfigure.accessExpirySeconds()).isNotNull();
		assertThat(jwtTokenConfigure.refreshExpirySeconds()).isNotNull();
		assertThat(jwtTokenConfigure.accessHeader()).isNotNull();
		assertThat(jwtTokenConfigure.refreshHeader()).isNotNull();
	}

	@DisplayName("access 토큰 만료시 TokenExpiredException 이 발생한다.")
	@Test
	void testAccessTokenExpired() throws InterruptedException {
		//given
		Jwt.Claims givenClaims = Jwt.Claims.of(1L, "testuser@naver.com", new String[] {"ROLE_GUEST"});
		String token = jwt.createForAccess(givenClaims);
		//when
		Thread.sleep(jwtTokenConfigure.accessExpirySeconds() * 2000L);
		//then
		assertThatThrownBy(() -> jwt.verify(token))
			.isInstanceOf(TokenExpiredException.class);
	}

	@DisplayName("refresh 토큰 만료시 TokenExpiredException 이 발생한다.")
	@Test
	void testRefreshTokenExpired() throws InterruptedException {
		//given
		Jwt.Claims givenClaims = Jwt.Claims.of(1L);
		String token = jwt.createForRefresh(givenClaims);
		//when
		Thread.sleep(jwtTokenConfigure.refreshExpirySeconds() * 2000L);
		//then
		assertThatThrownBy(() -> jwt.verify(token))
			.isInstanceOf(TokenExpiredException.class);
	}
}
