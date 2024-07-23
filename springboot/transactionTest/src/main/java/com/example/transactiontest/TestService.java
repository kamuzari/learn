package com.example.transactiontest;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.example.transactiontest.team.TeamEntity;
import com.example.transactiontest.team.TeamRepository;
import com.example.transactiontest.user.UserEntity;
import com.example.transactiontest.user.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class TestService {

	private final EntityManagerFactory entityManagerFactory;
	private final UserRepository userRepository;
	private final TeamRepository teamRepository;

	public String nonTransaction() {
		return TransactionSynchronizationManager.getCurrentTransactionName();
	}

	@Transactional
	public String transaction() {
		return TransactionSynchronizationManager.getCurrentTransactionName();
	}

	public List<Boolean> test() {
		List<TeamEntity> teams = teamRepository.findAll();
		List<UserEntity> byTeamIn = userRepository.findByTeamIn(teams);

		return teams.stream()
			.map(teamEntity -> entityManagerFactory.getPersistenceUnitUtil().isLoaded(teamEntity.getUsers()))
			.toList();
	}

	public List<Boolean> test2() {
		List<TeamEntity> teams = teamRepository.findAll();
		List<UserEntity> byTeamIn = userRepository.findByTeamIn(teams);

		return teams.stream()
			.map(teamEntity -> {
				teamEntity.getUsers().forEach(UserEntity::getName);
				return entityManagerFactory.getPersistenceUnitUtil().isLoaded(teamEntity.getUsers());
			})
			.toList();
	}


}
