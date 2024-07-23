package com.example.security.auth;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {

	@Modifying
	@Query("delete from JwtToken token where token.createdAt < :threshHold")
	void deleteExpiredToken(@Param("threshHold") LocalDateTime threshHold);
}
