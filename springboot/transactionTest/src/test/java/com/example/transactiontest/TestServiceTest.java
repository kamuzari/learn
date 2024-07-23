package com.example.transactiontest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.transactiontest.team.TeamEntity;
import com.example.transactiontest.team.TeamRepository;
import com.example.transactiontest.user.UserEntity;
import com.example.transactiontest.user.UserRepository;

@SpringBootTest
class TestServiceTest {

	@Autowired
	TestService testService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	TeamRepository teamRepository;
	@Autowired
	EntityManager entityManager;

	@Test
	@DisplayName("트랜잭션애노테이션없는 메소드를 호출하면 트랜잭션이름이 존재하지 않는다.")
	void testNonTransactionalName() {
		//given
		//when
		String transactionName = testService.nonTransaction();
		//then
		assertThat(transactionName).isNull();
	}

	@Test
	@DisplayName("트랜잭션애노테이션있는 메소드를 호출하면 트랜잭션이름이 존재한다.")
	void testTransactionalName() {
		//given
		//when
		String transactionName = testService.transaction();
		//then
		assertThat(transactionName).isNotNull();
	}

	@Test
	@DisplayName("따로 조회했는데 Team 객체에 사용자가 자동으로 로드되는지 테스트,"
		+ " 결과: 사용자는 따로 조회한 팀이 로드되는 반면, 팀은 자동으로 사용자를 로드하지 않는다.")
	void test1() {
		//given
		List<TeamEntity> teams = IntStream.range(0, 5)
			.mapToObj(value -> TeamEntity.builder().name(String.valueOf(value)).build())
			.toList();
		teamRepository.saveAll(teams);

		List<UserEntity> users = IntStream.range(0, 50)
			.mapToObj(value -> UserEntity.builder()
				.name(String.valueOf(value))
				.team(teams.get(value % 5)).build())
			.toList();
		userRepository.saveAll(users);
		//when
		List<Boolean> isLoads = testService.test();
		//then
		isLoads.forEach(isLoad -> assertThat(isLoad).isFalse());
	}

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
				user.addTeam(teams.get(value%5));
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

	@Test
	@Transactional
	@DisplayName("카테시안 곱?")
	void testCatesian(){
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
				user.addTeam(teams.get(value%5));
				return user;
			})
			.toList();
		userRepository.saveAll(users);
		entityManager.flush();
	    //when

		Set<TeamEntity> allCustom = teamRepository.findAllCustom();

		//then
		System.out.println(allCustom);
	}

}