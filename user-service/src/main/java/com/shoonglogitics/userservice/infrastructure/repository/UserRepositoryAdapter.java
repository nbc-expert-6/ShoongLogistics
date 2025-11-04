package com.shoonglogitics.userservice.infrastructure.repository;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.shoonglogitics.userservice.domain.entity.User;
import com.shoonglogitics.userservice.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

	private final JpaUserRepository jpaUserRepository;

	@Override
	public Optional<User> findByUsername(String username) {
		return jpaUserRepository.findByUserName(username);
	}

	@Override
	public User save(User user) {
		return jpaUserRepository.save(user);
	}
}
