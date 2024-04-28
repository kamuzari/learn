package com.example.security.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
public class TestController {

	@GetMapping("/only-login")
	public String access() {
		return "authorized!";
	}


}
