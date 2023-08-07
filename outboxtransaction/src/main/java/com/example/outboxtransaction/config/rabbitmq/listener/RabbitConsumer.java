package com.example.outboxtransaction.config.rabbitmq.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.outboxtransaction.product.domain.ProductEntity;
import com.example.outboxtransaction.product.domain.ProductRepository;
import com.example.outboxtransaction.product.service.ProductMessageDto;

@Transactional
@Component
public class RabbitConsumer {

	private static final Logger log = LoggerFactory.getLogger(RabbitConsumer.class.getName());
	private final ProductRepository productRepository;

	public RabbitConsumer(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@RabbitListener(queues = "searching-server")
	public void consume(ProductMessageDto message) {
		log.info("{}", message);

		try {
			ProductEntity product = productRepository.findById(message.productId())
				.orElseThrow(RuntimeException::new);
			product.acceptPublishForSearchingServer();
			log.info("@@@@@@@@@@@@@@@@@@@");
			log.info("product 비즈니스 수행..");
			log.info("@@@@@@@@@@@@@@@@@@@");

		} catch (Exception e) {
			log.info("잘못된 product data를 받았습니다.");
		}
	}
}
