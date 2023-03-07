package com.example.localstack;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;

import com.amazonaws.services.s3.AmazonS3;
import com.example.localstack.config.LocalStackS3Config;

@Import(LocalStackS3Config.class)
@SpringBootTest
public class Integration2Test {

	@Autowired
	AmazonS3 amazonS3;

	@Container
	static final GenericContainer<?> redis=new GenericContainer<>("redis:latest");

	static {
		redis.start();
	}
	@Value("${cloud.aws.s3.bucket}")
	String bucket;

	@Test
	@DisplayName("test")
	void test(){
	    //given
		amazonS3.createBucket(bucket);
	    //when
	    //then
	}
}
