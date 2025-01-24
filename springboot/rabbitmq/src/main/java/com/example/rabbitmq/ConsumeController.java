package com.example.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Component
public class ConsumeController {
	private static final Logger log = LoggerFactory.getLogger(ConsumeController.class);

	@RabbitListener(queues = "test.queue")
	public void consumeMessage(String message,
		Channel channel,
		@Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws Exception {
		log.warn("[Consumer] Received: (deliveryTag= {}), channel => {}", message, deliveryTag, channel);
		try {
			int processingTime = Integer.parseInt(message.split("-")[1]) * 1000;
			Thread.sleep(processingTime);

			log.error("[Consumer] Done: {}=> ACK sent, channel : {}", message, channel);
		} catch (Exception e) {
			channel.basicNack(deliveryTag, false, true);
		}
	}
}
