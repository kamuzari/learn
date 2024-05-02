package com.example.security.config.interceptor;

import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.example.security.jwt.Jwt;
import com.example.security.jwt.JwtTokenConfigure;
import com.example.security.user.controller.AccountController;
import com.example.security.user.controller.request.LoginRequestDto;
import com.example.security.user.controller.response.LoginResponseDto;
import com.example.security.user.controller.response.TokenDto;
import com.example.security.user.domain.User;
import com.example.security.user.domain.UserRepository;

import io.restassured.RestAssured;
import io.restassured.http.Header;

@ActiveProfiles("token")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthInterceptorTest {
	@LocalServerPort
	int port;

	@Autowired
	AuthInterceptor authInterceptor;

	@Autowired
	JwtTokenConfigure jwtTokenConfigure;

	@Autowired
	Jwt jwt;

	@Autowired
	AccountController accountController;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRepository;

	TokenDto accessTokenDto;
	TokenDto refreshTokenDto;
	User savedUser;

	@BeforeEach
	public void setUp() {
		String rawPassword = "docker!!1943";
		savedUser = userRepository.save(User.builder()
			.username("docker123")
			.password(passwordEncoder.encode(rawPassword))
			.build());
		LoginResponseDto response = (LoginResponseDto)accountController.login(new LoginRequestDto(
				savedUser.getUsername(),
				rawPassword
			)).getBody()
			.getResponse();
		accessTokenDto = response.access();
		refreshTokenDto = response.refresh();
	}

	@AfterEach
	public void redo() {
		userRepository.delete(savedUser);
	}

	@DisplayName("accessToken 이 만료되면 재발급한다.")
	@Test
	void testReIssueToken() throws Exception {
		//given
		long expiredTime = jwtTokenConfigure.accessExpirySeconds() * 3000L;
		Thread.sleep(expiredTime);
		Header accessTokenHeader = new Header(jwtTokenConfigure.accessHeader(), accessTokenDto.token());
		Header refreshTokenHeader = new Header(jwtTokenConfigure.refreshHeader(), refreshTokenDto.token());
		//when
		//then
		RestAssured.given().port(port)
			.header(accessTokenHeader).header(refreshTokenHeader)
			.contentType(JSON).log().all()
			.when().get("/auth/only-login")
			.then().log().all()
			.statusCode(200)
			.header(jwtTokenConfigure.accessHeader(), notNullValue())
			.header(jwtTokenConfigure.refreshHeader(), notNullValue())
			.header(jwtTokenConfigure.accessHeader(), not(accessTokenDto.token()))
			.header(jwtTokenConfigure.refreshHeader(), equalTo(refreshTokenDto.token()));
	}

	@DisplayName("refreshToken 까지 만료된다면 사용자에게 재인증을 요청하는 메시지를 응답한다.")
	@Test
	void failExpiredRefreshToken() throws InterruptedException {
		//given
		long expiredTime = jwtTokenConfigure.refreshExpirySeconds() * 3000L;
		Thread.sleep(expiredTime);
		Header accessTokenHeader = new Header(jwtTokenConfigure.accessHeader(), accessTokenDto.token());
		Header refreshTokenHeader = new Header(jwtTokenConfigure.refreshHeader(), refreshTokenDto.token());
		//when
		//then
		RestAssured.given().port(port)
			.header(accessTokenHeader).header(refreshTokenHeader)
			.contentType(JSON).log().all()
			.when().get("/auth/only-login")
			.then().log().all()
			.statusCode(200)
			.body("success", equalTo(false))
			.body("response.message", containsString("다시 로그인"));
	}

	@DisplayName("토큰 헤더가 존재하지 않으면 400 에러가 발생한다.")
	@Nested
	class InvalidNotTokenHeader {
		@DisplayName("access Token 이 담긴 헤더이름이 존재하지 않으면 예외가 발생한다.")
		@ParameterizedTest(name = "{index}: invalid headerName -> [{0}]")
		@ValueSource(strings = {"token", "goToken", "auth"})
		void failNotExistTokenAccessHeader(String accessTokenHeaderName) throws Exception {
			//given
			Header refreshTokenHeader = new Header(accessTokenHeaderName, accessTokenDto.token());
			Header accessTokenHeader = new Header(jwtTokenConfigure.refreshHeader(), refreshTokenDto.token());
			//when
			//then
			call(accessTokenHeader, refreshTokenHeader);
		}

		@DisplayName("refresh Token 이 담긴 헤더이름이 존재하지 않으면 예외가 발생한다.")
		@ParameterizedTest(name = "{index}: invalid headerName -> [{0}]")
		@ValueSource(strings = {"token", "goToken", "auth"})
		void failNotExistTokenForRefreshHeader(String refreshTokenHeaderName) throws Exception {
			//given
			Header refreshTokenHeader = new Header(refreshTokenHeaderName, accessTokenDto.token());
			Header accessTokenHeader = new Header(refreshTokenDto.headerName(), refreshTokenDto.token());
			//when
			//then
			call(accessTokenHeader, refreshTokenHeader);
		}

		private void call(Header accessTokenHeader, Header refreshTokenHeader) {
			RestAssured.given().port(port)
				.header(accessTokenHeader).header(refreshTokenHeader)
				.contentType(JSON).log().all()
				.when().get("/auth/only-login")
				.then().log().all()
				.assertThat()
				.statusCode(200)
				.body("success", equalTo(false));
		}
	}

}