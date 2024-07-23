package com.example.jpa.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.jpa.user.entity.UserEntity;

public interface CustomUserRepository {

	Page<UserEntity> findById(Long id, Pageable pageable);
}
