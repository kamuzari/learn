package com.example.localstack;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;

@Testcontainers
@SpringBootTest
public class IntegrationTest {

	private static final DockerImageName LOCALSTACK_IMAGE = DockerImageName.parse("localstack/localstack");

	@Container
	LocalStackContainer localStackContainer = new LocalStackContainer(LOCALSTACK_IMAGE)
		.withServices(S3); // testContainers 애노테이션 필요

	@Value("${cloud.aws.s3.bucket}")
	String bucket;

	@Test
	void test(){
		AmazonS3 amazonS3 = AmazonS3ClientBuilder
			.standard()
			.withEndpointConfiguration(localStackContainer.getEndpointConfiguration(S3))
			.withCredentials(localStackContainer.getDefaultCredentialsProvider())
			.build();

		amazonS3.createBucket(bucket);
		System.out.println(bucket +" 버킷 생성");

		String key = "foo-key";
		String content = "foo-content";
		amazonS3.putObject(bucket, key, content);
		System.out.println("파일을 업로드하였습니다. key=" + key +", content=" + content);

		S3Object object = amazonS3.getObject(bucket, key);
		System.out.println("파일을 가져왔습니다. = " + object.getKey());
	}
}
