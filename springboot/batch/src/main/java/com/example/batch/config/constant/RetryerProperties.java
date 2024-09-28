package com.example.batch.config.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "spring.client.config.default.retryer")
public record RetryerProperties(
	long period,
	long maxPeriod,
	int maxAttempts
) {
	@ConstructorBinding
	public RetryerProperties {
	}
}
