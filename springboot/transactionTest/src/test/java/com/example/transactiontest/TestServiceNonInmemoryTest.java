package com.example.transactiontest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import java.util.List;
import java.util.stream.IntStream;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.transactiontest.team.TeamEntity;
import com.example.transactiontest.team.TeamRepository;
import com.example.transactiontest.user.UserEntity;
import com.example.transactiontest.user.UserRepository;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
@SpringBootTest
public class TestServiceNonInmemoryTest {
	@Autowired
	TestService testService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	TeamRepository teamRepository;
	@Autowired
	EntityManager entityManager;

	@Test
	@DisplayName("그러면 영속성 컨텍스트에 해당 사용자가 있으니 쿼리는 날리는지 테스트하기 ")
	void tes2() {
		//given
		List<TeamEntity> teams = IntStream.range(0, 5)
			.mapToObj(value -> TeamEntity.builder().name(String.valueOf(value)).build())
			.toList();
		teamRepository.saveAll(teams);
		List<UserEntity> users = IntStream.range(0, 50)
			.mapToObj(value -> {
				UserEntity user = UserEntity.builder()
					.name(String.valueOf(value))
					.build();
				user.addTeam(teams.get(value % 5));
				return user;
			})
			.toList();
		userRepository.saveAll(users);
		entityManager.clear();
		//when
		List<Boolean> isLoads = testService.test2();
		//then
		isLoads.forEach(isLoad -> assertThat(isLoad).isTrue());
	}
}
