package com.shoonglogitics.userservice.application.strategy;

import org.springframework.stereotype.Service;

import com.shoonglogitics.userservice.application.command.HubManagerSignUpCommand;
import com.shoonglogitics.userservice.application.command.SignUpUserCommand;
import com.shoonglogitics.userservice.domain.entity.HubManager;
import com.shoonglogitics.userservice.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service(value = "HubManagerSignUpCommand")
@RequiredArgsConstructor
public class HubManagerSignUpStrategy implements SignUpStrategy {

	private final UserRepository userRepository;

	@Override
	public void signUp(SignUpUserCommand signUpUserCommand) {
		HubManagerSignUpCommand command = (HubManagerSignUpCommand)signUpUserCommand;

		if (userRepository.findByUsername(command.getUserName()).isPresent()) {
			throw new IllegalArgumentException("이미 존재하는 HUB_MANAGER 회원입니다.");
		}

		// HubManager 생성
		HubManager hubManager = HubManager.create(
			command.getUserName(),
			command.getPassword(),
			command.getEmail(),
			command.getName(),
			command.getSlackId(),
			command.getPhoneNumber(),
			command.getHubId()
		);

		userRepository.save(hubManager);
	}

}

