package com.example.security.exception.model;

public class ReLoginException extends BusinessException {
	public ReLoginException(String message, String externalMessage) {
		super(message, externalMessage);
	}

	public ReLoginException(String message, String externalMessage, Throwable cause) {
		super(message, cause, externalMessage);
	}
}
