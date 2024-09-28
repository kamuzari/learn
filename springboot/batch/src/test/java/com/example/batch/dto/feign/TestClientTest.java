package com.example.batch.dto.feign;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.*;

import java.net.ConnectException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.batch.config.constant.FeignProperties;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

import feign.RetryableException;

@SpringBootTest(
	properties = {
		"test.url=http://localhost:8089"
	}
)
class TestClientTest {

	static final String PATH = "/todos/1";

	WireMockServer wireMockServer;

	@Autowired
	FeignProperties feignProperties;

	@Autowired
	TestClient testClient;

	@BeforeEach
	public void setUp() {
		wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8089));
		wireMockServer.start();
		WireMock.configureFor("localhost", 8089);
	}

	@AfterEach
	public void tearDown() {
		wireMockServer.stop();
	}

	@DisplayName("외부 API 호출 후 200 상태 코드를 받는다")
	@Test
	void getPost() {
		// given
		stubFor(get(urlPathMatching(PATH))
			.willReturn(aResponse()
				.withStatus(200)
				.withBody("SUCCESS")));

		// when
		String post = testClient.getPost(1);

		// then
		assertThat(post).isNotNull();
	}

	@DisplayName("내가 지정한 재시도 횟수와 동일하게 시도한다")
	@Test
	public void testRetry() {
		// given
		stubFor(get(urlPathMatching(PATH))
			.willReturn(aResponse().withStatus(500)));

		// when, then
		Assertions.assertThatThrownBy(() -> {
			testClient.getPost(1);
		}).isInstanceOf(RetryableException.class);
		verify(feignProperties.retryer().maxAttempts(), getRequestedFor(urlPathMatching(PATH)));
	}

	@DisplayName("연결 시간이 초과될 경우 RetryableException 이 발생하고 connect 관련 메시지를 남긴다")
	@Test
	public void testConnectTimeout() {
		// given
		wireMockServer.stop();

		// when, then
		Assertions.assertThatThrownBy(() -> {
				testClient.getPost(1);
			}).isInstanceOf(RetryableException.class)
			.hasMessageContaining("Connect");
	}

	@DisplayName("응답 시간이 초과될 경우 RetryableException 이 발생하고 read time out 관련 메시지를 남긴다.")
	@Test
	public void testReadTimeout() {
		// given
		stubFor(get(urlPathMatching(PATH))
			.willReturn(aResponse()
				.withStatus(200)
				.withFixedDelay(feignProperties.readTimeout()+1000))); // 응답을 15초 지연시켜 readTimeout 발생

		// when, then
		Assertions.assertThatThrownBy(() -> {
				testClient.getPost(1);
			}).isInstanceOf(RetryableException.class)
			.hasMessageContaining("Read timed out");
	}

	@DisplayName("재시도 후에도 실패하면 Fallback이 호출된다")
	@Test
	public void testFallbackAfterRetries() {
		// given: 서버에서 계속 500 상태 코드 반환
		stubFor(get(urlPathMatching(PATH))
			.willReturn(aResponse().withStatus(500)));


		// when, then: 재시도 후에도 실패하면 Fallback이 호출되는지 확인
		String result = testClient.getPost(1);
		assertThat(result).isEqualTo("Fallback exception");
	}
}