package com.example.oauth.user.service;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.oauth.user.domain.entity.UserEntity;
import com.example.oauth.user.domain.repository.UserRepository;

@Transactional(readOnly = true)
@Service
public class AuthService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final UserRepository userRepository;

	public AuthService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Optional<UserEntity> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public Optional<UserEntity> findByProviderAndProviderId(String provider, String providerId) {
		return userRepository.findByProviderAndProviderId(provider, providerId);
	}

	@Transactional
	public UserEntity join(OAuth2User oauth2User, String authorizedClientRegistrationId) {

		String providerId = oauth2User.getName();
		return findByProviderAndProviderId(authorizedClientRegistrationId, providerId)
			.map(user -> {
				log.warn("Already exists: {} for (provider: {}, providerId: {})", user, authorizedClientRegistrationId,
					providerId);
				return user;
			})
			.orElseGet(() -> {
				Map<String, Object> attributes = oauth2User.getAttributes();
				@SuppressWarnings("unchecked")
				Map<String, Object> properties = (Map<String, Object>)attributes.get("properties");

				String nickname = (String)properties.get("nickname");
				String profileImage = (String)properties.get("profile_image");
				// Group group = groupRepository.findByName("USER_GROUP")
				// 	.orElseThrow(() -> new IllegalStateException("Could not found group for USER_GROUP"));
				return userRepository.save(
					new UserEntity(nickname, authorizedClientRegistrationId, providerId, profileImage)
				);
			});
	}
}