package com.example.jpa;

import java.util.List;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.assertj.core.api.Assertions;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import net.datafaker.Faker;

import com.example.jpa.user.entity.LockerEntity;
import com.example.jpa.user.entity.UserEntity;
import com.example.jpa.user.repository.LockerRepository;
import com.example.jpa.user.repository.UserRepository;


@DataJpaTest
public class OneToOneTest {

	private static final Faker faker = new Faker();

	@PersistenceContext
	EntityManager em;

	@Autowired
	UserRepository userRepository;

	@Autowired
	LockerRepository lockerRepository;

	List<UserEntity> users;
	List<LockerEntity> lockers;

	@BeforeEach
	void setUp() {
		users = Stream.generate(() ->
				UserEntity.builder().
					name(faker.superhero().name())
					.build()
			)
			.limit(20)
			.map(user -> userRepository.save(user))
			.toList();

		lockers = users.stream().filter(user -> user.getId() % 2 == 0)
			.map(user -> {
				LockerEntity lockerEntity = lockerRepository.save(
					LockerEntity.builder()
						.serial(faker.random().hex(8))
						.build());
				user.grantLocker(lockerEntity);

				return lockerEntity;
			}).toList();
		em.flush();
		em.clear();
	}

	@Test
	@DisplayName("외래키가 주테이블에 있는 경우 fetch 타입이 Lazy일때, 주테이블을 조회할 때 N+1이 발생하지 않는다.")
	void test() {
		//given
		//when
		List<UserEntity> foundUsers = userRepository.findAll();
		//then
		foundUsers.stream()
			.filter(user -> user.getId() % 2 == 0)
			.forEach(user -> {
				Assertions.assertThat(Hibernate.isInitialized(user.getLockerEntity())).isFalse();
			});
	}

	@Test
	@DisplayName("외래키가 주테이블에 있는 경우 fetch 타입이 Lazy일때, 주테이블이 아닌곳을 조회할 때는?")
	void test2() {
		//given
		//when
		List<LockerEntity> all = lockerRepository.findAll();
		//then
		all.forEach(locker -> {
			Assertions.assertThat(locker).isNotNull();
		});
	}

}
