package com.example.outboxtransaction.product.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ProductEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	private boolean isToSearchingServer = false;

	protected ProductEntity() {

	}

	public ProductEntity(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void acceptPublishForSearchingServer() {
		this.isToSearchingServer = true;
	}
}
