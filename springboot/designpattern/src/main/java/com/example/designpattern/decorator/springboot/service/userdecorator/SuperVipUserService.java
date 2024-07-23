package com.example.designpattern.decorator.springboot.service.userdecorator;

import org.springframework.stereotype.Service;

import com.example.designpattern.decorator.springboot.UserStatus;
import com.example.designpattern.decorator.springboot.service.UserDefaultService;

@Service
public class SuperVipUserService extends UserServiceDecorator {
	public SuperVipUserService(UserDefaultService userDefaultService) {
		super(userDefaultService);
	}

	@Override
	public void before() {
		System.out.println("**** SUPER VIP는 일반 사용자보다 특별하게 전처리 ****");
	}

	@Override
	public void after() {
		System.out.println("**** SUPER VIP는 일반 사용자보다 특별하게 후처리 ****");
	}

	@Override
	public UserStatus getStatus() {
		return UserStatus.SUPERVIP;
	}
}
