package com.shoonglogitics.userservice.application.strategy.signup;

import org.springframework.stereotype.Service;

import com.shoonglogitics.userservice.application.command.MasterSignUpCommand;
import com.shoonglogitics.userservice.application.command.SignUpUserCommand;
import com.shoonglogitics.userservice.domain.entity.Master;
import com.shoonglogitics.userservice.domain.entity.SignupStatus;
import com.shoonglogitics.userservice.domain.entity.UserRole;
import com.shoonglogitics.userservice.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service(value = "MasterSignUpCommand")
@RequiredArgsConstructor
public class MasterSignUpStrategy implements SignUpStrategy {

	private final UserRepository userRepository;

	@Override
	public void signUp(SignUpUserCommand signUpUserCommand) {
		MasterSignUpCommand command = (MasterSignUpCommand)signUpUserCommand;

		if (userRepository.findByUsername(command.getUserName()).isPresent()) {
			throw new IllegalArgumentException("이미 존재하는 MASTER 회원입니다.");
		}

		Master master = Master.builder()
			.userName(command.getUserName())
			.password(command.getPassword())
			.userRole(UserRole.MASTER)
			.signupStatus(SignupStatus.PENDING)
			.email(command.getEmail())
			.name(command.getName())
			.slackId(command.getSlackId())
			.phoneNumber(command.getPhoneNumber())
			.build();

		userRepository.save(master);
	}
}

