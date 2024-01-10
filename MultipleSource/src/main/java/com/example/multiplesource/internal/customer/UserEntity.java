package com.example.multiplesource.internal.customer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	protected UserEntity() {
	}

	public UserEntity(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}
