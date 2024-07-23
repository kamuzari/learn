package com.example.designpattern.decorator.springboot.service;

import org.springframework.stereotype.Service;

import com.example.designpattern.decorator.springboot.UserStatus;

@Service
public class UserDefaultService implements UserService {
	@Override
	public void doSomeThing() {
		System.out.println("회원 서비스 제공!");
	}

	@Override
	public UserStatus getStatus() {
		return UserStatus.NORMAL;
	}

}
