package com.happyfree.eventtutorial;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.happyfree.eventtutorial.step2.domain.OrderCancelHistory;
import com.happyfree.eventtutorial.step2.domain.infra.OrderCancelHistoryRepository;
import com.happyfree.eventtutorial.step2.service.OrderService;
import com.happyfree.eventtutorial.tutorial.basic.CustomSpringEventPublisher;

import jakarta.persistence.EntityManager;

@SpringBootApplication
public class EventTutorialApplication {

	@Autowired
	private OrderCancelHistoryRepository orderCancelHistoryRepository;

	@Autowired
	private OrderService orderService;

	public static void main(String[] args) {
		SpringApplication.run(EventTutorialApplication.class, args);
	}

	@Bean
	public ApplicationRunner applicationRunner(CustomSpringEventPublisher eventPublisher) {
		return args -> {

			// do: basic application event
			/*
			eventPublisher.publishEvent(
				MessageFormat.format("event send .. by kamuzari time - {0}", LocalDateTime.now())
			);
			*/

			// do: domain event
			/*
			Order newOrder = new Order(UUID.randomUUID().toString(), ShipStatus.ACCEPTED);
			newOrder.cancel();
			*/

			// do: domain event with transaction
			String newOrderId = orderService.createOrder();
			Thread.sleep(2000);

			orderService.cancelOrder(newOrderId);
		};
	}

}
