package com.shoonglogitics.userservice.application.strategy.signup;

import org.springframework.stereotype.Service;

import com.shoonglogitics.userservice.application.command.CompanyManagerSignUpCommand;
import com.shoonglogitics.userservice.application.command.SignUpUserCommand;
import com.shoonglogitics.userservice.domain.entity.CompanyManager;
import com.shoonglogitics.userservice.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service(value = "CompanyManagerSignUpCommand")
@RequiredArgsConstructor
public class CompanyManagerSignUpStrategy implements SignUpStrategy {

	private final UserRepository userRepository;

	@Override
	public void signUp(SignUpUserCommand signUpUserCommand) {
		CompanyManagerSignUpCommand command = (CompanyManagerSignUpCommand)signUpUserCommand;

		if (userRepository.findByUsername(command.getUserName()).isPresent()) {
			throw new IllegalArgumentException("이미 존재하는 COMPANY_MANAGER 회원입니다.");
		}

		// CompanyManager 생성 (User 상속 포함)
		CompanyManager companyManager = CompanyManager.create(
			command.getUserName(),
			command.getPassword(),
			command.getCompanyId(),
			command.getEmail(),
			command.getName(),
			command.getSlackId(),
			command.getPhoneNumber()
		);

		userRepository.save(companyManager);
	}

}

