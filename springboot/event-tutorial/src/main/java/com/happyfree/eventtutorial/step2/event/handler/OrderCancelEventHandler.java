package com.happyfree.eventtutorial.step2.event.handler;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.happyfree.eventtutorial.step2.event.model.OrderCanceledEvent;
import com.happyfree.eventtutorial.step2.service.ExteriorService;

@Service
public class OrderCancelEventHandler {
	private final ExteriorService exteriorService;

	public OrderCancelEventHandler(ExteriorService exteriorService) {
		this.exteriorService = exteriorService;
	}

	@EventListener(OrderCanceledEvent.class)
	public void handle(OrderCanceledEvent event) {
		exteriorService.refund(event.getOrderId());
	}
}
