package com.tutorial.elasticsearch.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutorial.elasticsearch.domain.Product;
import com.tutorial.elasticsearch.domain.ProductDocumentRepository;
import com.tutorial.elasticsearch.domain.ProductRepository;
import com.tutorial.elasticsearch.dto.request.ProductRequestDto;
import com.tutorial.elasticsearch.dto.response.ProductResponseDto;

@Transactional(readOnly = true)
@Service
public class ProductService {

	private final ProductRepository productRepository;
	private final ProductDocumentRepository productDocumentRepository;

	public ProductService(ProductRepository productRepository, ProductDocumentRepository productDocumentRepository) {
		this.productRepository = productRepository;
		this.productDocumentRepository = productDocumentRepository;
	}

	@Transactional
	public void save(ProductRequestDto requestDto) {
		Product product = new Product(requestDto.name(), requestDto.description(), requestDto.price());
		productRepository.save(product);
		productDocumentRepository.save(product.createDocument());
	}

	public List<ProductResponseDto> findAllInMySQL(String keyword) {
		List<Product> products = productRepository.searchByKeyword(keyword);
		return products.stream()
			.map(product -> new ProductResponseDto(product.getId(), product.getName(), product.getDescription(),
				product.getPrice(), product.getCreatedDate(), product.getLastModifiedDate()))
			.collect(Collectors.toList());
	}

	public List<ProductResponseDto> findAllInElasticsearch(String keyword) {
		List<Product> products = productDocumentRepository.findByNameContainingOrDescriptionContaining(keyword,
			keyword);
		return products.stream()
			.map(product -> new ProductResponseDto(product.getId(), product.getName(), product.getDescription(),
				product.getPrice(), product.getCreatedDate(), product.getLastModifiedDate()))
			.collect(Collectors.toList());
	}
}
