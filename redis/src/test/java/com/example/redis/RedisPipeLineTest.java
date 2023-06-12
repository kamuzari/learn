package com.example.redis;

import java.util.List;
import java.util.stream.IntStream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@SpringBootTest
public class RedisPipeLineTest extends RedisContainerTest {

	@Autowired
	RedisTemplate<String, String> redisTemplate;

	String keyPrefix = "product::1::";

	@Test
	@DisplayName("5개의 데이터를 한번의 커넥션 이후로 가져온다.")
	void testPipeLine() {
		//given
		String keyPrefix = "product::1::";
		RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
		//when
		List<String> answers = redisTemplate.executePipelined((RedisCallback<Object>)redisConnection -> {
				IntStream.rangeClosed(1, 5)
					.forEach(value -> {
						String key = keyPrefix + value;
						redisConnection.stringCommands().get(stringSerializer.serialize(key));
					});
				return null;
			}).stream().map(String::valueOf)
			.toList();
		//then
		Assertions.assertThat(answers.size()).isEqualTo(5);
	}

	private void setData(String keyPrefix) {
		redisTemplate.opsForValue().set(keyPrefix + "1", "100");
		redisTemplate.opsForValue().set(keyPrefix + "2", "200");
		redisTemplate.opsForValue().set(keyPrefix + "3", "300");
		redisTemplate.opsForValue().set(keyPrefix + "4", "400");
		redisTemplate.opsForValue().set(keyPrefix + "5", "500");
		IntStream.rangeClosed(1, 5)
			.forEach(value -> {
				String s = redisTemplate.opsForValue().get(keyPrefix + value);
			});
	}
}
