package com.example.alarm.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.example.alarm.config.RabbitMqConfig;
import com.example.alarm.domain.NotificationMessage;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class RabbitMqService {
	private final Sinks.Many<NotificationMessage> sink = Sinks.many().multicast().onBackpressureBuffer();


	// consumer 역할을 함.
	@RabbitListener(queues = RabbitMqConfig.NOTIFICATION_QUEUE)
	public void receiveMessage(NotificationMessage notification) {
		sink.tryEmitNext(notification);
	}

	public Flux<NotificationMessage> getNotificationFlux() {
		return sink.asFlux();
	}
}
