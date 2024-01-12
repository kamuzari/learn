package com.example.multiplesource.exterior.enterprise;

import java.util.StringJoiner;

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

	public EnterpriseEntity(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", EnterpriseEntity.class.getSimpleName() + "[", "]")
				.add("id=" + id)
				.add("name='" + name + "'")
				.toString();
	}
}
