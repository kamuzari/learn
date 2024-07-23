package com.example.outboxtransaction.product.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.example.outboxtransaction.config.rabbitmq.MessageQueue;

@Service
public class ProductMessageService {

	private final RabbitTemplate rabbitTemplate;

	public ProductMessageService(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void sendToSearchingServer(MessageQueue destination, ProductMessageDto productMessageDto) {
		rabbitTemplate.convertAndSend(destination.getName(), productMessageDto);
	}
}
