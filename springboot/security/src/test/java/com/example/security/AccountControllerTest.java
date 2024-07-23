package com.example.security;

import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.io.IOException;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.security.jwt.JwtTokenConfigure;
import com.example.security.user.controller.request.LoginRequestDto;
import com.example.security.user.domain.User;
import com.example.security.user.domain.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerTest {

	@LocalServerPort
	int port;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	JwtTokenConfigure jwtTokenConfigure;

	@DisplayName("로그인을 하면 token 2개를 발급받는다.")
	@Test
	void testLogin() throws IOException {
		//given
		String rawPassword = "123test123";
		User savedUser = userRepository.save(
			User.builder()
				.username("docker123")
				.password(encoder.encode(rawPassword))
				.build()
		);
		var request = new LoginRequestDto(savedUser.getUsername(), rawPassword);

		//when
		//then
		RestAssured.given().port(port).body(request).contentType(JSON).log().all().
			when().post("/api/v1/account/auth")
			.then().log().all()
			.assertThat()
			.statusCode(HttpStatus.SC_OK)
			.body("success", equalTo(true))
			.body("response.access", notNullValue())
			.body("response.access.headerName", equalTo(jwtTokenConfigure.accessHeader()))
			.body("response.access.token", notNullValue())
			.body("response.refresh.headerName", equalTo(jwtTokenConfigure.refreshHeader()))
			.body("response.refresh.token", notNullValue());
	}
}
