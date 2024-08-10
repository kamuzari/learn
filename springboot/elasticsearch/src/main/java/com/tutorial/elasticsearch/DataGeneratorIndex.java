package com.tutorial.elasticsearch;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.javafaker.Faker;
import com.tutorial.elasticsearch.domain.Product;
import com.tutorial.elasticsearch.domain.ProductDocument;
import com.tutorial.elasticsearch.domain.ProductDocumentRepository;
import com.tutorial.elasticsearch.domain.ProductRepository;

import jakarta.annotation.PostConstruct;

@Component
public class DataGeneratorIndex {
	private static final Logger logger = LoggerFactory.getLogger(DataGenerator.class);

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductDocumentRepository productDocumentRepository;

	private Faker faker = new Faker();

	@PostConstruct
	@Transactional
	public void generateData() {
		List<Product> products = new ArrayList<>();
		for (int i = 0; i < 100000; i++) {
			String price = faker.number().digit();
			Product product = new Product(
				faker.commerce().productName(),
				faker.lorem().sentence(),
				Long.parseLong(price)
			);

			products.add(product);
		}

		// Save to MySQL
		List<Product> created = productRepository.saveAll(products);
		logger.error("products ==> {}", products);
		// Save to Elasticsearch

		List<ProductDocument> documents = created.stream()
			.map(product -> product.createDocument())
			.toList();
		productDocumentRepository.saveAll(documents);
		logger.error("document ==> {}", documents);
	}
}
