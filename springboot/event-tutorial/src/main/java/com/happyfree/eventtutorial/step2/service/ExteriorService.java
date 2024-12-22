package com.happyfree.eventtutorial.step2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExteriorService {

	private static final Logger logger = LoggerFactory.getLogger(ExteriorService.class);

	public void refund(String orderId) {
		logger.error("Refund Order Id: {}", orderId);
	}
}
