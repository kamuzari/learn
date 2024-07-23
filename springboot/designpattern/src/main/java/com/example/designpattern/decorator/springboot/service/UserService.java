package com.example.designpattern.decorator.springboot.service;

import com.example.designpattern.decorator.springboot.UserStatus;

public interface UserService {
	void doSomeThing();

	UserStatus getStatus();
}
