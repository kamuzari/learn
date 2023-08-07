package com.example.outboxtransaction.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.datafaker.Faker;

import com.example.outboxtransaction.config.rabbitmq.MessageQueue;
import com.example.outboxtransaction.product.domain.ProductEntity;
import com.example.outboxtransaction.product.domain.ProductRepository;

@Transactional(readOnly = true)
@Service
public class ProductService {

	private final ProductRepository productRepository;

	private final ProductMapper productMapper;
	private final ProductMessageService messageService;

	public ProductService(ProductRepository productRepository, ProductMapper productMapper,
		ProductMessageService messageService) {
		this.productRepository = productRepository;
		this.productMapper = productMapper;
		this.messageService = messageService;
	}

	@Transactional
	public void create() {
		Faker faker = new Faker();
		String productName = faker.commerce().productName();
		ProductEntity productEntity = new ProductEntity(productName);
		ProductEntity savedProduct = productRepository.save(productEntity);
		ProductMessageDto productMessageDto = productMapper.toProductMessageDto(savedProduct);
		messageService.sendToSearchingServer(MessageQueue.SEARCHING_SERVER, productMessageDto);
		throw new RuntimeException("Fatal Error...");
	}

	@Transactional
	public void createForOutBoxTransaction() {
		Faker faker = new Faker();
		String productName = faker.commerce().productName();
		ProductEntity productEntity = new ProductEntity(productName);
		productRepository.save(productEntity);
	}
}
