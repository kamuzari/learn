package com.happyfree.eventtutorial.step2.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.happyfree.eventtutorial.step2.domain.Order;
import com.happyfree.eventtutorial.step2.domain.OrderCancelHistory;
import com.happyfree.eventtutorial.step2.domain.ShipStatus;
import com.happyfree.eventtutorial.step2.domain.infra.OrderCancelHistoryRepository;
import com.happyfree.eventtutorial.step2.domain.infra.OrderRepository;

@Service
public class ExteriorService {

	private static final Logger logger = LoggerFactory.getLogger(ExteriorService.class);
	private final OrderRepository orderRepository;
	private final OrderCancelHistoryRepository orderCancelHistoryRepository;

	public ExteriorService(OrderRepository orderRepository, OrderCancelHistoryRepository orderCancelHistoryRepository) {
		this.orderRepository = orderRepository;
		this.orderCancelHistoryRepository = orderCancelHistoryRepository;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void refund(String orderId) {
		logger.error("Refund Order Id: {}", orderId);

		Order requestedOrder = orderRepository.findById(orderId).orElseThrow(RuntimeException::new);
		logger.info("주문 취소 이벤트 발행한 order --> {}", requestedOrder);
		if (!ShipStatus.CANCELED.equals(requestedOrder.getShipStatus())) {
			throw new RuntimeException("트랜잭션 커밋 처리가 안되었습니다.");
		}

		OrderCancelHistory refundedOrder = orderCancelHistoryRepository.save(
			new OrderCancelHistory(UUID.randomUUID().toString(),
				requestedOrder.getId()));

		logger.error("order cancel history: {}", refundedOrder);
	}
}
