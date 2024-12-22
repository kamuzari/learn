package com.happyfree.eventtutorial.step2.domain;

import java.time.LocalDateTime;
import java.util.StringJoiner;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class OrderCancelHistory {

	@Id
	private String id;

	// @OneToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "order_id")
	// private Order order;

	String orderId;

	private LocalDateTime createdAt = LocalDateTime.now();

	protected OrderCancelHistory() {
	}

	public OrderCancelHistory(String id, String orderId) {
		this.id = id;
		this.orderId = orderId;
	}

	// public OrderCancelHistory(String id, Order order) {
	// 	this.id = id;
	// 	this.order = order;
	// }

	public String getId() {
		return id;
	}

	public String getOrderId() {
		return orderId;
	}

	// public Order getOrder() {
	// 	return order;
	// }

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", OrderCancelHistory.class.getSimpleName() + "[", "]")
			.add("id='" + id + "'")
			.add("order=" + orderId)
			.add("createdAt=" + createdAt)
			.toString();
	}
}
