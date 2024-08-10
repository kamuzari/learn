package com.tutorial.kafka.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import com.tutorial.kafka.domain.Message;
import com.tutorial.kafka.service.RoomService;

@RestController
public class MessageController {
	private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

	private static final String FIXED_TOPIC_NAME = "topic1";
	private final RoomService roomService;
	private final KafkaTemplate<Integer, String> kafkaTemplate;
	private final SimpMessagingTemplate messagingTemplate;

	public MessageController(KafkaTemplate<Integer, String> kafkaTemplate, RoomService roomService,
		SimpMessagingTemplate messagingTemplate) {
		this.kafkaTemplate = kafkaTemplate;
		this.roomService = roomService;
		this.messagingTemplate = messagingTemplate;
	}

	@MessageMapping("/hello") // publish uri
	public void greeting(@Header("simpSessionId") String sessionId,
		@Header("roomId") String roomId,
		Message message) {
		messagingTemplate.convertAndSend("/topic/" + roomId,
			new Message("Hello, " + message.getName()));
		logger.info("[chatting]    sessionId -> {}, roomId -> {}", sessionId, roomId);

	}

	@MessageMapping("/leave")
	public Message leave(@Header("simpSessionId") String sessionId,
		@Header("roomId") String roomId,
		Message message) {
		roomService.remove(roomId, sessionId);
		logger.info("sessionId -> {}, roomId -> {}", sessionId, roomId);
		logger.info("[remove]    sessionId -> {}, roomId -> {}", sessionId, roomId);

		return new Message("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
	}

	@GetMapping("/send")
	public void send() {
		kafkaTemplate.send(FIXED_TOPIC_NAME, "send test");
	}

}
