package com.example.security.exception.model;

public class NotFoundResource extends RuntimeException {
	public NotFoundResource(String message) {
		super(message);
	}
}
