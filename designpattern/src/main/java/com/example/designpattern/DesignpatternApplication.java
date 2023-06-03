package com.example.designpattern;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.DependsOn;

import com.example.designpattern.decorator.springboot.UserStatus;
import com.example.designpattern.decorator.springboot.controller.UserController;

@SpringBootApplication
public class DesignpatternApplication implements CommandLineRunner {

	public DesignpatternApplication(UserController userController) {
		this.userController = userController;
	}

	public static void main(String[] args) {
		SpringApplication.run(DesignpatternApplication.class, args);
	}

	private final UserController userController;

	@Override
	public void run(String... args) throws Exception {
		Arrays.stream(UserStatus.values())
			.forEach(userStatus -> {
				System.out.println("userStatus -> "+ userStatus);
				userController.go(userStatus.name());
				System.out.println();
			});

		System.exit(1);
	}
}
