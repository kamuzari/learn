package com.tutorial.kafka.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	String roomId;

	String sessionId;

	protected Room() {
	}

	public Room(String roomId, String sessionId) {
		this.roomId = roomId;
		this.sessionId = sessionId;
	}

	public Long getId() {
		return id;
	}

	public String getRoomId() {
		return roomId;
	}

	public String getSessionId() {
		return sessionId;
	}
}
