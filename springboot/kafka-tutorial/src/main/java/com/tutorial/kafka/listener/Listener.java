package com.tutorial.kafka.listener;

import org.springframework.kafka.annotation.KafkaListener;

public class Listener {
	@KafkaListener(id = "listen1", topics = "topic1",groupId = "a")
	public void listen1(String in) {
		System.out.println("listen1: " + in);
		System.out.println(in);
	}

	@KafkaListener(id = "listen2", topics = "topic1",groupId = "a")
	public void listen2(String in) {
		System.out.println("listen2: " + in);
		System.out.println(in);
	}
}
