package com.happyfree.eventtutorial.step2.event.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.happyfree.eventtutorial.step2.domain.infra.OrderCancelHistoryRepository;
import com.happyfree.eventtutorial.step2.event.model.OrderCanceledEvent;
import com.happyfree.eventtutorial.step2.service.ExteriorService;

@Service
public class OrderCancelEventHandler {
	private static final Logger logger = LoggerFactory.getLogger(OrderCancelEventHandler.class);
	private final OrderCancelHistoryRepository orderCancelHistoryRepository;
	private final ExteriorService exteriorService;

	public OrderCancelEventHandler(OrderCancelHistoryRepository orderCancelHistoryRepository,
		ExteriorService exteriorService) {
		this.orderCancelHistoryRepository = orderCancelHistoryRepository;
		this.exteriorService = exteriorService;
	}

	@TransactionalEventListener(
		phase = TransactionPhase.AFTER_COMMIT
	)
	@EventListener(OrderCanceledEvent.class)
	public void handle(OrderCanceledEvent event) {
		logger.info("======== expected previous transaction commited ======");

		exteriorService.refund(event.getOrderId());

	}
}
