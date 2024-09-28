package com.example.batch.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

	private static final Logger log = LoggerFactory.getLogger(CustomErrorDecoder.class);

	@Override
	public Exception decode(String methodKey, Response response) {
		FeignException exception = feign.FeignException.errorStatus(methodKey, response);
		int status = response.status();
		if (status >= 500) {
			log.warn("[Retry] Error Message: {}", exception.getMessage());
			return new RetryableException(
				response.status(),
				exception.getMessage(),
				response.request().httpMethod(),
				exception,
				(Long)null,
				response.request());
		}

		throw new RuntimeException("error");
	}
}
