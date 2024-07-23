package com.example.transactiontest.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.transactiontest.team.TeamEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	List<UserEntity> findByTeamIn(List<TeamEntity> teams);
}
