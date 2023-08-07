package com.example.outboxtransaction.config.rabbitmq;

public enum MessageQueue {
	SEARCHING_SERVER("searching-server");

	private final String name;

	MessageQueue(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
