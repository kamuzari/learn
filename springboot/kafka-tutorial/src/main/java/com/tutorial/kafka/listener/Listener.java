package com.tutorial.kafka.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

public class Listener {
	private static Logger logger = LoggerFactory.getLogger(Listener.class);

	@KafkaListener(id = "listen1", topics = "topic1", groupId = "a")
	public void listen1(String in) {
		logger.info("[id = listen1]:  {}", in);
	}

	@KafkaListener(id = "listen2", topics = "topic1", groupId = "b")
	public void listen2(String in) {
		logger.info("[id = listen2]:  {}", in);
	}
}
