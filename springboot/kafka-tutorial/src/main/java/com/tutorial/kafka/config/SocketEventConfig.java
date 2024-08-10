package com.tutorial.kafka.config;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.tutorial.kafka.service.RoomService;

@Component
public class SocketEventConfig {

	private final RoomService sessionService;

	public SocketEventConfig(RoomService sessionService) {
		this.sessionService = sessionService;
	}

	@EventListener
	public void handleWebSocketConnectListener(SessionConnectEvent event) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
		String sessionId = accessor.getSessionId();
		String roomId = accessor.getFirstNativeHeader("roomId");

		sessionService.create(sessionId, roomId);
	}

	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
		String sessionId = accessor.getSessionId();
		String roomId = accessor.getFirstNativeHeader("roomId");

		sessionService.remove(sessionId, roomId);
	}
}
