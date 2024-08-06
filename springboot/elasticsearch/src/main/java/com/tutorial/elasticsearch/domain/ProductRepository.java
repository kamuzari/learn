package com.tutorial.elasticsearch.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("SELECT p FROM Product p WHERE p.name LIKE %?1% OR p.description LIKE %?1%")
	List<Product> searchByKeyword(String keyword);
}
