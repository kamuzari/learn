package com.tutorial.elasticsearch.dto.response;

import java.time.LocalDateTime;

public record ProductResponseDto(
	Long id,
	String name,
	String description,
	Long price,
	LocalDateTime createdAt,
	LocalDateTime modifiedAt
) {
}
