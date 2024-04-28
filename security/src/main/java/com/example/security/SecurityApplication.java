package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.security.user.domain.User;
import com.example.security.user.domain.UserRepository;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class SecurityApplication {
	// @Autowired
	// DataBundle dataBundle;

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	// @PostConstruct
	// public void init(){
	// 	dataBundle.addUser();
	// }
	//
	// @Component
	// public static class DataBundle {
	// 	@Autowired
	// 	PasswordEncoder passwordEncoder;
	// 	@Autowired
	// 	UserRepository userRepository;
	// 	void addUser(){
	// 		String encode = passwordEncoder.encode("123");
	// 		System.out.println("##############");
	// 		System.out.println(encode);
	// 		System.out.println("##############");
	// 		User test123 = userRepository.save(User.builder()
	// 			.username("test123")
	// 			.password(encode)
	// 			.build());
	// 	}
	// }

}
