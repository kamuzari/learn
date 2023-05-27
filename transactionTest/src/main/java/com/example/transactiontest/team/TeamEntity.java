package com.example.transactiontest.team;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.transactiontest.user.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(exclude = {"users"})
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "team")
@Entity
public class TeamEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	@Builder.Default
	@OneToMany(fetch = FetchType.LAZY)
	private Set<UserEntity> users = new LinkedHashSet<>();

	public void addUser(UserEntity user) {
		users.add(user);
	}
}
