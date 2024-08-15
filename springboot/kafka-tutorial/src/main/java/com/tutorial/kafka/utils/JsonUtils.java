package com.tutorial.kafka.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.NotSupportedException;

public class JsonUtils {
	private static final ObjectMapper objectMapper = new ObjectMapper();

	private JsonUtils() {
	}

	public static String toJson(Object object) throws JsonProcessingException {
		return objectMapper.writeValueAsString(object);
	}

	public static <T> T fromJson(String json, Class<T> classType) throws JsonProcessingException {
		return objectMapper.readValue(json, classType);
	}
}
