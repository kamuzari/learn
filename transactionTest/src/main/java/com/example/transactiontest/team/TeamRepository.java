package com.example.transactiontest.team;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TeamRepository extends JpaRepository<TeamEntity, Long> {

	@Query(value = "select t from TeamEntity t join fetch t.users")
	Set<TeamEntity> findAllCustom();
}
