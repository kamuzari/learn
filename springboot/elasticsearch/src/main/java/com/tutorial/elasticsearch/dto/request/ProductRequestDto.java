package com.tutorial.elasticsearch.dto.request;

public record ProductRequestDto(
	Long id,
	String name,
	String description,
	Long price
) {
}
