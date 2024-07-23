package com.example.oauth.user.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.oauth.user.domain.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

  Optional<Group> findByName(String name);

}