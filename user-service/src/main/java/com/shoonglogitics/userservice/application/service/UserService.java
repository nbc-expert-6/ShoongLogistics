package com.shoonglogitics.userservice.application.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoonglogitics.userservice.application.command.SignUpUserCommand;
import com.shoonglogitics.userservice.application.dto.PageResponse;
import com.shoonglogitics.userservice.application.strategy.signup.SignUpStrategy;
import com.shoonglogitics.userservice.application.strategy.view.CompanyManagerViewStrategy;
import com.shoonglogitics.userservice.application.strategy.view.HubManagerViewStrategy;
import com.shoonglogitics.userservice.application.strategy.view.MasterViewStrategy;
import com.shoonglogitics.userservice.application.strategy.view.ShipperViewStrategy;
import com.shoonglogitics.userservice.application.strategy.view.UserViewStrategy;
import com.shoonglogitics.userservice.domain.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	private final Map<String, SignUpStrategy> signUpStrategyMap;

	private final Map<String, UserViewStrategy<?>> userViewStrategyMap;

	// 생성자에서 Strategy Map 초기화
	public UserService(
		UserRepository userRepository,
		MasterViewStrategy masterViewStrategy,
		HubManagerViewStrategy hubManagerViewStrategy,
		ShipperViewStrategy shipperViewStrategy,
		CompanyManagerViewStrategy companyManagerViewStrategy,
		Map<String, SignUpStrategy> signUpStrategyMap
	) {
		this.userRepository = userRepository;
		this.signUpStrategyMap = signUpStrategyMap;

		// Map 구성
		this.userViewStrategyMap = Map.of(
			"MASTER", masterViewStrategy,
			"HUB_MANAGER", hubManagerViewStrategy,
			"SHIPPER", shipperViewStrategy,
			"COMPANY_MANAGER", companyManagerViewStrategy
		);
	}

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

	@Transactional(readOnly = true)
	public <T> PageResponse<T> getUsers(String roleKey, Pageable pageable, UUID hubId) {
		UserViewStrategy<T> strategy = (UserViewStrategy<T>)userViewStrategyMap.get(roleKey);

		if (strategy == null) {
			throw new IllegalArgumentException("지원하지 않는 역할입니다 : " + roleKey);
		}

		Page<T> users = strategy.findUsers(pageable, hubId);
		return PageResponse.of(users);
	}

}
