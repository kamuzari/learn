package com.example.multiplesource.exterior.enterprise;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class EnterpriseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	protected EnterpriseEntity() {
	}

	public EnterpriseEntity(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}
