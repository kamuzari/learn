package com.example.security.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.security.utils.ApiUtils;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	@ExceptionHandler({TokenExpiredException.class, JWTVerificationException.class})
	public ApiUtils.ApiResult handle(JWTVerificationException e) {
		logger.info(e.getMessage());
		return ApiUtils.error(e.getMessage());
	}
}
