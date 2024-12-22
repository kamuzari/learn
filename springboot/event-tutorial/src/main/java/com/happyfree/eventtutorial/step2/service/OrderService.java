package com.happyfree.eventtutorial.step2.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.happyfree.eventtutorial.step2.domain.Order;
import com.happyfree.eventtutorial.step2.domain.ShipStatus;
import com.happyfree.eventtutorial.step2.domain.infra.OrderRepository;

@Service
public class OrderService {
	private final OrderRepository orderRepository;

	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Transactional
	public void cancelOrder(String orderId) {
		Order order = orderRepository.findById(orderId).orElseThrow(RuntimeException::new);
		order.cancel();
	}

	@Transactional
	public String createOrder() {
		return orderRepository.save(new Order(UUID.randomUUID().toString(), ShipStatus.ACCEPTED))
			.getId();
	}
}
