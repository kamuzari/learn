package com.example.security.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class TestController {

	@GetMapping("/only-login")
	public String access() {
		return "authorized!";
	}


}
