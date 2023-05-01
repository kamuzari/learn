package com.example.oauth.user.domain.entity;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "users")
public class UserEntity {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "provider")
	private String provider;

	@Column(name = "provider_id")
	private String providerId;

	@Column(name = "profile_image")
	private String profileImage;

	protected UserEntity() {/*no-op*/}

	public UserEntity(String username, String provider, String providerId, String profileImage) {

		this.username = username;
		this.provider = provider;
		this.providerId = providerId;
		this.profileImage = profileImage;
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getProvider() {
		return provider;
	}

	public String getProviderId() {
		return providerId;
	}

	public Optional<String> getProfileImage() {
		return ofNullable(profileImage);
	}

	public String getGroup() {
		return "ROLE_USER";
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("id", id)
			.append("username", username)
			.append("provider", provider)
			.append("providerId", providerId)
			.append("profileImage", profileImage)
			.toString();
	}

}