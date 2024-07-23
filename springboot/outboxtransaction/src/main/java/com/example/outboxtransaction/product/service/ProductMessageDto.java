package com.example.outboxtransaction.product.service;

import java.util.StringJoiner;

public record ProductMessageDto(
	Long productId,
	String productName
) {
}
