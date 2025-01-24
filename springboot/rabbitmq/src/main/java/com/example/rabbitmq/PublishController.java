package com.example.rabbitmq;

import java.util.stream.IntStream;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublishController {

	private final AmqpTemplate amqpTemplate;

	public PublishController(AmqpTemplate amqpTemplate) {
		this.amqpTemplate = amqpTemplate;
	}

	@PostMapping("/send")
	public String sendMessages(@RequestParam(defaultValue = "5") int count) {
		IntStream.rangeClosed(1, count).
			mapToObj(String::valueOf).
			forEach(id -> {
				String message = String.join("-", "MSG", id);
				amqpTemplate.convertAndSend("test.queue", message);
			});

		return "Sent " + count + " messages to [test.queue]";
	}

}
