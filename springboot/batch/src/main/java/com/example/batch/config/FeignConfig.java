package com.example.batch.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.batch.config.constant.FeignProperties;

import feign.Feign;
import feign.Logger;
import feign.Request;
import feign.Retryer;
import feign.codec.ErrorDecoder;

@EnableFeignClients(basePackages = "com.example.batch.dto.feign")
@EnableConfigurationProperties(FeignProperties.class)
@Configuration
public class FeignConfig {

	private final FeignProperties feignProperties;

	@Bean
	public ErrorDecoder errorDecoder() {
		return new CustomErrorDecoder();
	}

	public FeignConfig(FeignProperties feignProperties) {
		this.feignProperties = feignProperties;
	}

	@Bean
	public Retryer retryer() {
		return new Retryer.Default(
			feignProperties.retryer().period(),
			feignProperties.retryer().maxPeriod(),
			feignProperties.retryer().maxAttempts()
		);
	}

	@Bean
	public Request.Options options() {
		return new Request.Options(
			feignProperties.connectTimeout(),
			feignProperties.readTimeout()
		);
	}

	@Bean
	public Feign.Builder feignBuilder(Retryer retryer, Request.Options options, ErrorDecoder errorDecoder) {
		return Feign.builder()
			.retryer(retryer)
			.options(options)
			.errorDecoder(errorDecoder);
	}
}
