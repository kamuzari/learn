package com.tutorial.kafka.controller;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import com.tutorial.kafka.domain.Message;

@RestController
public class MessageController {

	private static final String FIXED_TOPIC_NAME = "topic1";
	private final KafkaTemplate<Integer, String> template;

	public MessageController(KafkaTemplate<Integer, String> kafkaTemplate) {
		this.template = kafkaTemplate;
	}

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Message greeting(Message message) {
		return new Message("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
	}

	@GetMapping("/send")
	public void send() {
		template.send(FIXED_TOPIC_NAME, "send test");
	}
}
