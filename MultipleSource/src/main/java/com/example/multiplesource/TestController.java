package com.example.multiplesource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.multiplesource.exterior.enterprise.EnterpriseEntity;
import com.example.multiplesource.exterior.enterprise.EnterpriseRepository;
import com.example.multiplesource.internal.customer.UserEntity;
import com.example.multiplesource.internal.customer.UserRepository;

@RequestMapping("/api")
@RestController
public class TestController {

	private static int number = 0;
	private final EnterpriseRepository enterpriseRepository;
	private final UserRepository userRepository;

	public TestController(EnterpriseRepository enterpriseRepository, UserRepository userRepository) {
		this.enterpriseRepository = enterpriseRepository;
		this.userRepository = userRepository;
	}

	@GetMapping("/enterprise")
	public String saveEnterprise() {
		EnterpriseEntity enterpriseEntity = enterpriseRepository.save(
				new EnterpriseEntity("enterpriseEntityTest" + number));
		return enterpriseEntity.toString();
	}

	@GetMapping("/customer")
	public String saveCustomer() {
		UserEntity userEntity = userRepository.save(new UserEntity("userEntityTest" + number));
		return userEntity.toString();
	}
}
