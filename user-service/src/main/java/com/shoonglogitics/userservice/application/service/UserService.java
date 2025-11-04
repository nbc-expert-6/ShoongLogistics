package com.shoonglogitics.userservice.application.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoonglogitics.userservice.application.command.SignUpUserCommand;
import com.shoonglogitics.userservice.application.strategy.SignUpStrategy;
import com.shoonglogitics.userservice.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	private final Map<String, SignUpStrategy> signUpStrategyMap;

	@Transactional
	public void signUp(SignUpUserCommand command) {
		if (userRepository.findByUsername(command.getUserName()).isPresent()) {
			throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
		}

		String typeKey = command.getClass().getSimpleName();
		SignUpStrategy strategy = signUpStrategyMap.get(typeKey);

		if (strategy == null) {
			throw new IllegalArgumentException("지원하지 않는 유형입니다.");
		}

		strategy.signUp(command);
	}

}
