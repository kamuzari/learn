package com.happyfree.eventtutorial.tutorial.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class CustomSpringEventPublisher {

	private static final Logger logger = LoggerFactory.getLogger(CustomSpringEventPublisher.class);

	private final ApplicationEventPublisher applicationEventPublisher;

	public CustomSpringEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.applicationEventPublisher = applicationEventPublisher;
	}

	public void publishEvent(String message) {
		logger.warn("CustomSpringEventPublisher = {}", message);
		applicationEventPublisher.publishEvent(new CustomSpringEvent(this, message));
	}
}
