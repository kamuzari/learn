package com.example.jpa.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>, CustomUserRepository {

}
