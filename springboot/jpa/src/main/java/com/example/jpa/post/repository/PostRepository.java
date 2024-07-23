package com.example.jpa.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.post.entity.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
