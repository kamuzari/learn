package com.example.outboxtransaction.product.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.outboxtransaction.product.service.ProductService;

@RequestMapping("api/products")
@RestController
public class ProductController {

	private ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping("/v1")
	public ResponseEntity<String> create() {
		productService.create();

		return ResponseEntity.ok("success");
	}

	@PostMapping("/v2")
	public ResponseEntity<String> createForOutBoxTransaction() {
		productService.createForOutBoxTransaction();

		return ResponseEntity.ok("success");
	}
}
