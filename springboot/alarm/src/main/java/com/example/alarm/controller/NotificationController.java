package com.example.alarm.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.alarm.domain.NotificationMessage;
import com.example.alarm.service.NotificationSenderService;
import com.example.alarm.service.RabbitMqService;

import reactor.core.publisher.Flux;

@RestController
public class NotificationController {
	private final RabbitMqService rabbitMqService;
	private final NotificationSenderService senderService;

	public NotificationController(RabbitMqService rabbitMqService, NotificationSenderService senderService) {
		this.rabbitMqService = rabbitMqService;
		this.senderService = senderService;
	}


	@GetMapping(value = "/sse/notifications", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<NotificationMessage> getNotificationFlux() {
		return rabbitMqService.getNotificationFlux();
	}

	@PostMapping("/sendNotification")
	public ResponseEntity<Void> sendNotification(@RequestBody NotificationMessage notificationMessage) {
		senderService.sendNotification(notificationMessage);
		return ResponseEntity.ok().build();
	}
}
