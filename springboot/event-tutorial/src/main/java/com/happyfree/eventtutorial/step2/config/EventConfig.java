package com.happyfree.eventtutorial.step2.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.happyfree.eventtutorial.step2.event.producer.EventsProducer;

@Configuration
public class EventConfig {

	private final ApplicationContext applicationContext;

	public EventConfig(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Bean
	public InitializingBean initializeEvents(){
		return ()-> EventsProducer.setPublisher(applicationContext);
	}
}
