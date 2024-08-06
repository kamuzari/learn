package com.tutorial.elasticsearch.domain;

import java.time.LocalDateTime;
import java.util.StringJoiner;

import org.apache.commons.lang3.builder.ToStringSummary;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "products")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private String description;

	private Long price;

	@CreatedDate
	private LocalDateTime createdDate;

	@LastModifiedDate
	private LocalDateTime lastModifiedDate;

	public Product(String name, String description, Long price) {
		this.name = name;
		this.description = description;
		this.price = price;
	}

	protected Product(Long id, String name, String description, Long price, LocalDateTime createdDate,
		LocalDateTime lastModifiedDate) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
	}

	protected Product() {
	}

	public ProductDocument createDocument() {
		return new ProductDocument(this);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Long getPrice() {
		return price;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", Product.class.getSimpleName() + "[", "]")
			.add("id=" + id)
			.add("name='" + name + "'")
			.add("description='" + description + "'")
			.add("price=" + price)
			.add("createdDate=" + createdDate)
			.add("lastModifiedDate=" + lastModifiedDate)
			.toString();
	}
}
