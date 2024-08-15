package com.tutorial.kafka.controller.dto.request;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

public record ChatMessage(
	@Header("simpSessionId")
	String senderId,
	@Header("roomId")
	String roomId,

	@Payload(required = true)
	String content
) {
}
