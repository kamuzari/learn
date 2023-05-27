package com.example.jpa.user.repository;

import static com.example.jpa.post.entity.QPostEntity.postEntity;
import static com.example.jpa.user.entity.QUserEntity.userEntity;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import com.example.jpa.user.entity.UserEntity;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomUserRepositoryImpl implements CustomUserRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Page<UserEntity> findById(Long id, Pageable pageable) {
		List<UserEntity> contents = jpaQueryFactory.selectFrom(userEntity)
			.join(userEntity.posts, postEntity).fetchJoin()
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.where(userEntity.id.eq(id))
			.fetch();

		JPAQuery<Long> count = jpaQueryFactory.select(userEntity.count())
			.from(userEntity)
			.join(userEntity.posts, postEntity)
			.where(userEntity.id.eq(id));

		return PageableExecutionUtils.getPage(contents, pageable, count::fetchOne);
	}
}
