package com.example.batch.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "com.example.batch.dto.feign")
@Configuration
public class FeignConfig {

}
