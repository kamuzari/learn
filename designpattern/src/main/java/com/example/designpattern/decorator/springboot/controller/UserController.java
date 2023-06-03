package com.example.designpattern.decorator.springboot.controller;

import java.util.List;

import org.springframework.context.annotation.DependsOn;
import org.springframework.web.bind.annotation.RestController;

import com.example.designpattern.decorator.springboot.service.UserService;

@RestController
public class UserController {

	private final List<UserService> userServices;

	public UserController(List<UserService> userServices) {
		this.userServices = userServices;
	}

	public void go(String status) {
		userServices.stream()
			.filter(userService -> userService.getStatus().name().equalsIgnoreCase(status))
			.findAny()
			.orElseThrow(() -> new RuntimeException("회원 상태가 정의되지 않는 형태로 입력되었습니다."))
			.doSomeThing();
	}
}
