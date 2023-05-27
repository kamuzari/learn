package com.example.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import net.datafaker.Faker;

import com.example.jpa.config.QueryDslConfig;
import com.example.jpa.post.entity.PostEntity;
import com.example.jpa.post.repository.PostRepository;
import com.example.jpa.user.entity.UserEntity;
import com.example.jpa.user.repository.UserRepository;

@Import(QueryDslConfig.class)
@DataJpaTest
public class OneToManyTest {

	Faker faker = new Faker();

	@Autowired
	UserRepository userRepository;

	@Autowired
	PostRepository postRepository;

	@Test
	@DisplayName("내가 작성한 게시글 가져오기")
	void testGetMyPosts() {
		//given
		List<UserEntity> users = Stream.generate(() -> faker.name().fullName())
			.limit(5)
			.map(name -> userRepository.save(UserEntity.builder()
				.name(name)
				.build()))

			.toList();
		List<PostEntity> posts = new ArrayList<>();
		users.forEach(user -> {
			List<PostEntity> createdPosts = Stream.generate(() -> postRepository.save(PostEntity.builder()
					.title(faker.book().title())
					.contents(faker.commerce().productName())
					.user(user)
					.build())
				).limit(50)
				.toList();

			posts.addAll(createdPosts);
		});

		//when

		PageRequest pageRequest = PageRequest.of(1, 10);
		Page<UserEntity> pageUser = userRepository.findById(users.get(0).getId(), pageRequest);
		//then
	}
}
