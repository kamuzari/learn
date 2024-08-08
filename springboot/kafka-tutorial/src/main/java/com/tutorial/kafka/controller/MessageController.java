package com.tutorial.kafka.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import com.tutorial.kafka.domain.Message;

@RestController
public class MessageController {

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Message greeting(Message message) throws Exception {
		return new Message("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
	}
}
