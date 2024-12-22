package com.happyfree.eventtutorial.step2.event.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;

public class EventsProducer {
	private static final Logger logger = LoggerFactory.getLogger(EventsProducer.class);
	private static ApplicationEventPublisher publisher;

	public static void setPublisher(ApplicationEventPublisher publisher) {
		EventsProducer.publisher = publisher;
	}

	public static void raise(Object event) {
		logger.warn("raise event: {}", event);

		if (publisher != null) {
			publisher.publishEvent(event);
		}
	}
}
