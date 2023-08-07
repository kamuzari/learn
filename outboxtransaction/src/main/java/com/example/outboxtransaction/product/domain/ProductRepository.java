package com.example.outboxtransaction.product.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
	@Query("SELECT p FROM ProductEntity p WHERE p.isToSearchingServer = false")
	Optional<ProductEntity> findFirstByIsToSearchingServerFalse();
}
