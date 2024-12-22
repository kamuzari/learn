package com.happyfree.eventtutorial.step2.event.model;

public class OrderCanceledEvent extends CommonEvent {
	private String orderId;

	public OrderCanceledEvent(String orderId) {
		super();
		this.orderId = orderId;
	}

	public String getOrderId() {
		return orderId;
	}
}
