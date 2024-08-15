package com.tutorial.kafka.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorial.kafka.controller.dto.request.ChatMessage;
import com.tutorial.kafka.domain.Message;

@Component
public class ChatListener {
	private static Logger logger = LoggerFactory.getLogger(ChatListener.class);
	private final SimpMessagingTemplate messagingTemplate;

	public ChatListener(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	@org.springframework.kafka.annotation.KafkaListener(id = "listen1", topics = "topic1")
	public void listen1(String in) throws JsonProcessingException {
		logger.info("[id = listen1]:  {}", in);
		ObjectMapper objectMapper = new ObjectMapper();
		ChatMessage chatMessage = objectMapper.readValue(in, new TypeReference<>() {
		});


		messagingTemplate.convertAndSend("/chatter/" + chatMessage.roomId(),
			new Message(chatMessage.senderId()+": " + chatMessage.content()));

	}

	// @org.springframework.kafka.annotation.KafkaListener(id = "listen2", topics = "topic1", groupId = "a")
	// public void listen2(String in) {
	// 	logger.info("[id = listen2]:  {}", in);
	// }
}
