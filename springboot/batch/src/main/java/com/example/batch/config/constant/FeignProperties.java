package com.example.batch.config.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "spring.client.config.default")
public record FeignProperties(
	int connectTimeout,
	int readTimeout,
	RetryerProperties retryer
) {
	@ConstructorBinding
	public FeignProperties {
	}
}
