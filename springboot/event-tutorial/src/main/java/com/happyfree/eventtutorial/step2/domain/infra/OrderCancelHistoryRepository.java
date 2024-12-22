package com.happyfree.eventtutorial.step2.domain.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import com.happyfree.eventtutorial.step2.domain.OrderCancelHistory;

public interface OrderCancelHistoryRepository extends JpaRepository<OrderCancelHistory, String> {
}
