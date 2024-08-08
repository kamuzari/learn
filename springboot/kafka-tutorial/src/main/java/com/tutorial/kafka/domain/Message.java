package com.tutorial.kafka.domain;

public class Message {
	private String name;

	protected Message() {
	}

	public Message(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
