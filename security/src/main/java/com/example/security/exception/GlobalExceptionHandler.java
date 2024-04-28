package com.example.security.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.security.utils.ApiUtils;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@ExceptionHandler({JWTVerificationException.class})
	public ApiUtils.ApiResult handle(JWTVerificationException e) {
		logger.info(e.getMessage());
		return ApiUtils.error("인증 오류가 발생하였습니다.");
	}

	@ExceptionHandler(JWTCreationException.class)
	public ApiUtils.ApiResult handle(JWTCreationException e) {
		logger.info(e.getMessage());
		return ApiUtils.error("관리자에게 문의해주세요.");
	}
}
