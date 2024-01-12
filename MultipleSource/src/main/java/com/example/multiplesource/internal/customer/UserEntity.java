package com.example.multiplesource.internal.customer;

import java.util.StringJoiner;

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

	public UserEntity(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", UserEntity.class.getSimpleName() + "[", "]")
				.add("id=" + id)
				.add("name='" + name + "'")
				.toString();
	}
}
