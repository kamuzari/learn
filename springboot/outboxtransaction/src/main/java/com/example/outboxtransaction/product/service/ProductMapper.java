package com.example.outboxtransaction.product.service;

import org.springframework.stereotype.Component;

import com.example.outboxtransaction.product.domain.ProductEntity;

@Component
public class ProductMapper {

	public ProductMessageDto toProductMessageDto(ProductEntity productEntity) {
		return new ProductMessageDto(
			productEntity.getId(),
			productEntity.getName()
		);
	}
}
