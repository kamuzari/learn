package com.happyfree.eventtutorial.step2.event.model;

import java.time.LocalDateTime;

public abstract class CommonEvent {
	private LocalDateTime createdAt =LocalDateTime.now();

	protected CommonEvent() {

	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}
