package com.example.alarm.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.example.alarm.config.RabbitMqConfig;
import com.example.alarm.domain.NotificationMessage;
import com.example.alarm.domain.NotificationMessageRepository;

@Service
public class NotificationSenderService {

	private final RabbitTemplate rabbitTemplate;
	private final NotificationMessageRepository messageRepository;

	public NotificationSenderService(RabbitTemplate rabbitTemplate, NotificationMessageRepository messageRepository) {
		this.rabbitTemplate = rabbitTemplate;
		this.messageRepository = messageRepository;
	}

	public void sendNotification(NotificationMessage message) {
		messageRepository.save(message);
		rabbitTemplate.convertAndSend(RabbitMqConfig.NOTIFICATION_QUEUE, message);
	}
}
