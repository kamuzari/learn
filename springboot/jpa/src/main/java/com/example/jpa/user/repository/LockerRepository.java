package com.example.jpa.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.user.entity.LockerEntity;

public interface LockerRepository extends JpaRepository<LockerEntity, Long> {
}
