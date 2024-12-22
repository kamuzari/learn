package com.happyfree.eventtutorial.tutorial.basic;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CustomSpringEventListener {

	private static final Logger logger = LoggerFactory.getLogger(CustomSpringEventListener.class);

	@EventListener
	public void onApplicationEvent(CustomSpringEvent event) {
		System.out.println(LocalDateTime.now());
		logger.error("onApplicationEvent = {}", event.getMessage());
	}

	@EventListener
	public void onApplicationEvent2(CustomSpringEvent event) {
		System.out.println(LocalDateTime.now());
		logger.error("onApplicationEvent2 = {}", event.getMessage());
	}
}
