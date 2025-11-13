package com.shoonglogitics.userservice.application.strategy.signup;

import org.springframework.stereotype.Service;

import com.shoonglogitics.userservice.application.command.ShipperSignUpCommand;
import com.shoonglogitics.userservice.application.command.SignUpUserCommand;
import com.shoonglogitics.userservice.domain.entity.Shipper;
import com.shoonglogitics.userservice.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service(value = "ShipperSignUpCommand")
@RequiredArgsConstructor
public class ShipperSignUpStrategy implements SignUpStrategy {

	private final UserRepository userRepository;

	@Override
	public void signUp(SignUpUserCommand signUpUserCommand) {
		ShipperSignUpCommand command = (ShipperSignUpCommand)signUpUserCommand;

		if (userRepository.findByUsername(command.getUserName()).isPresent()) {
			throw new IllegalArgumentException("이미 존재하는 SHIPPER 회원입니다.");
		}

		ShipperSignUpCommand shipperSignUpCommand = (ShipperSignUpCommand)signUpUserCommand;

		Integer lastOrder = userRepository.findLastShipperOrderByHubId(shipperSignUpCommand.getHubId())
			.orElse(0);
		System.out.println("lastOrder = " + lastOrder);

		Integer newOrder = lastOrder + 1;

		// Shipper 객체를 직접 생성
		Shipper shipper = Shipper.create(
			command.getUserName(),
			command.getPassword(),
			command.getHubId(),
			command.getEmail(),
			command.getName(),
			command.getSlackId(),
			command.getPhoneNumber(),
			command.getShipperType(),
			newOrder,
			command.getIsShippingAvailable()
		);

		userRepository.save(shipper);
	}

}


