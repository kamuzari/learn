package com.tutorial.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

@SpringBootApplication
public class KafkaTutorialApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaTutorialApplication.class, args);
	}

	@Bean
	public NewTopic kafkaTopic() {
		return TopicBuilder.name("topic1")
			.partitions(1)
			.replicas(1)
			.build();
	}
}
