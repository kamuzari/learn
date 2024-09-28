package com.example.batch.dto.feign;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.batch.config.FeignConfig;

@FeignClient(
	name = "TestClient",
	url = "${test.url}",
	configuration = FeignConfig.class,
	fallbackFactory = TestClient.TestFallbackFactory.class
)
public interface TestClient {
	@GetMapping(value = "/todos/{postId}", consumes = "application/json")
	String getPost(
		@PathVariable("postId") int postId
	);

	@Component
	class TestFallbackFactory implements FallbackFactory<String> {
		private static final Logger log = LoggerFactory.getLogger(TestFallbackFactory.class);

		@Override
		public String create(Throwable cause) {
			log.error("Fallback exception : {}", cause);
			return "Fallback exception";
		}
	}
}
