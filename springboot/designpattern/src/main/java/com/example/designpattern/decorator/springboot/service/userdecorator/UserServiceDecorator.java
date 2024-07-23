package com.example.designpattern.decorator.springboot.service.userdecorator;

import com.example.designpattern.decorator.springboot.service.UserService;
import com.example.designpattern.decorator.springboot.service.UserDefaultService;

public abstract class UserServiceDecorator implements UserService {
	private final UserDefaultService userDefaultService;

	public UserServiceDecorator(UserDefaultService userDefaultService) {
		this.userDefaultService = userDefaultService;
	}

	@Override
	public void doSomeThing() {
		this.before();
		this.userDefaultService.doSomeThing();
		this.after();
	}

	public abstract void before();

	public abstract void after();
}
