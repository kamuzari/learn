package com.tutorial.cdc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class Consumer {
	private static final Logger log = LoggerFactory.getLogger(Consumer.class);

	private final ObjectMapper objectMapper;

	public Consumer(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	@KafkaListener(topics = "dbserver1.mydb.outbox_event", groupId = "my-cdc-group")
	public void consumeOutboxEvent(String message) {
		try {
			log.info("\n\n Received Outbox Event: {}",
				objectMapper.convertValue(
					objectMapper.readTree(message).path("after"),
					EventDto.class)
			);
		} catch (Exception e) {
			log.error("Error parsing or sending message to RabbitMQ", e);
		}
	}
}
