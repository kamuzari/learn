package com.tutorial.elasticsearch.domain;

import java.time.LocalDateTime;
import java.util.StringJoiner;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "products")
public class ProductDocument {
	private Long id;

	private String name;

	private String description;

	private Long price;

	private LocalDateTime createdDate;

	private LocalDateTime lastModifiedDate;

	public ProductDocument(Product product) {
		this.id = product.getId();
		this.name = product.getName();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.createdDate = product.getCreatedDate();
		this.lastModifiedDate = product.getLastModifiedDate();
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", ProductDocument.class.getSimpleName() + "[", "]")
			.add("id=" + id)
			.add("name='" + name + "'")
			.add("description='" + description + "'")
			.add("price=" + price)
			.add("createdDate=" + createdDate)
			.add("lastModifiedDate=" + lastModifiedDate)
			.toString();
	}
}
