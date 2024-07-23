package com.example.oauth.user.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.oauth.user.domain.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByUsername(String username);

	@Query("select u from UserEntity u where u.provider = :provider and u.providerId = :providerId")
	Optional<UserEntity> findByProviderAndProviderId(String provider, String providerId);
}