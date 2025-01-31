package com.tutorial.cdc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class CdcApplication {

	public static void main(String[] args) {
		SpringApplication.run(CdcApplication.class, args);
	}

}
