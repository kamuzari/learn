package com.tutorial.elasticsearch.domain;

import org.hibernate.annotations.Index;
import org.springframework.stereotype.Indexed;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Indexed
@Entity
public class YourEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@FullTextField
	private String field1;

	private String field2;

	// Getters and setters
}

