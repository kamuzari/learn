package com.tutorial.elasticsearch.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.elasticsearch.dto.request.ProductRequestDto;
import com.tutorial.elasticsearch.dto.response.ProductResponseDto;
import com.tutorial.elasticsearch.service.ProductService;

@RequestMapping("/products")
@RestController
public class ProductController {
	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping
	public void registerProduct(@RequestBody ProductRequestDto requestDto) {
		productService.save(requestDto);
	}

	@GetMapping("/search/mysql")
	public List<ProductResponseDto> searchProductsInMySQL(@RequestParam String keyword) {
		return productService.findAllInMySQL(keyword);
	}

	@GetMapping("/search/elasticsearch")
	public List<ProductResponseDto> searchProductsInElasticsearch(@RequestParam String keyword) {
		return productService.findAllInElasticsearch(keyword);
	}
}
