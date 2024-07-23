package com.example.outboxtransaction.product.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.outboxtransaction.config.rabbitmq.MessageQueue;
import com.example.outboxtransaction.product.domain.ProductEntity;
import com.example.outboxtransaction.product.domain.ProductRepository;

@Transactional(readOnly = true)
@Component
public class ProductScheduleService {
	private static final Logger log = LoggerFactory.getLogger(ProductScheduleService.class.getName());
	private final ProductMessageService productMessageService;
	private final ProductRepository productRepository;

	public ProductScheduleService(ProductMessageService productMessageService, ProductRepository productRepository) {
		this.productMessageService = productMessageService;
		this.productRepository = productRepository;
	}

	@Scheduled(cron = "*/10 * * * * *")
	public void sendToSearchingServerForProduct() {
		log.info("@@@@@@@@@@@@@@@@@@@@@@@polling product data...@@@@@@@@@@@@@@@@@@@@");
		try {
			ProductEntity productEntity = productRepository.findFirstByIsToSearchingServerFalse()
				.orElseThrow(RuntimeException::new);
			ProductMessageDto productMessageDto = new ProductMessageDto(productEntity.getId(), productEntity.getName());
			productMessageService.sendToSearchingServer(MessageQueue.SEARCHING_SERVER,productMessageDto);
		} catch (RuntimeException e) {
			log.info("not exist publish product data...");
		}
	}
}
