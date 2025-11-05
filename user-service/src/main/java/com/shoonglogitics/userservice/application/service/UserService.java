package com.shoonglogitics.userservice.application.service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoonglogitics.userservice.application.command.LoginUserCommand;
import com.shoonglogitics.userservice.application.command.SignUpUserCommand;
import com.shoonglogitics.userservice.application.dto.LoginUserResponseDto;
import com.shoonglogitics.userservice.application.dto.PageResponse;
import com.shoonglogitics.userservice.application.strategy.signup.SignUpStrategy;
import com.shoonglogitics.userservice.application.strategy.view.CompanyManagerViewStrategy;
import com.shoonglogitics.userservice.application.strategy.view.HubManagerViewStrategy;
import com.shoonglogitics.userservice.application.strategy.view.MasterViewStrategy;
import com.shoonglogitics.userservice.application.strategy.view.ShipperViewStrategy;
import com.shoonglogitics.userservice.application.strategy.view.UserViewStrategy;
import com.shoonglogitics.userservice.domain.entity.SignupStatus;
import com.shoonglogitics.userservice.domain.entity.User;
import com.shoonglogitics.userservice.domain.repository.UserRepository;
import com.shoonglogitics.userservice.security.JwtProvider;

@Service
public class UserService {

	private final UserRepository userRepository;

	private final Map<String, SignUpStrategy> signUpStrategyMap;

	private final Map<String, UserViewStrategy<?>> userViewStrategyMap;

	private final JwtProvider jwtProvider;

	// 생성자에서 Strategy Map 초기화
	public UserService(
		UserRepository userRepository,
		MasterViewStrategy masterViewStrategy,
		HubManagerViewStrategy hubManagerViewStrategy,
		ShipperViewStrategy shipperViewStrategy,
		CompanyManagerViewStrategy companyManagerViewStrategy,
		Map<String, SignUpStrategy> signUpStrategyMap, JwtProvider jwtProvider
	) {
		this.userRepository = userRepository;
		this.signUpStrategyMap = signUpStrategyMap;
		this.jwtProvider = jwtProvider;

		// Map 구성
		this.userViewStrategyMap = Map.of(
			"MASTER", masterViewStrategy,
			"HUB_MANAGER", hubManagerViewStrategy,
			"SHIPPER", shipperViewStrategy,
			"COMPANY_MANAGER", companyManagerViewStrategy
		);
	}

	public LoginUserResponseDto loginUser(LoginUserCommand command) {
		User user = userRepository.findByUsername(command.getUserName())
			.orElseThrow(() -> new UsernameNotFoundException("해당 User를 찾을 수 없습니다."));

		if (!user.getSignupStatus().equals(SignupStatus.APPROVED)) {
			throw new IllegalArgumentException("회원가입이 승인된 사용자만 로그인 가능합니다.");
		}

		String accessToken = jwtProvider.createAccessToken(user.getId(), user.getUserRole());
		return LoginUserResponseDto.of(accessToken, user.getId());
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

	@Transactional(readOnly = true)
	public <T> Optional<T> getUser(String roleKey, Long userId) {
		UserViewStrategy<T> strategy = (UserViewStrategy<T>)userViewStrategyMap.get(roleKey);
		if (strategy == null) {
			throw new IllegalArgumentException("지원하지 않는 역할입니다 : " + roleKey);
		}

		return strategy.findUserById(userId);
	}

	@Transactional(readOnly = true)
	public User findUser(Long id) {
		return userRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 User를 찾을 수 없습니다."));
	}

	@Transactional
	public void updateSignupStatus(Long id, String status) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 User를 찾을 수 없습니다."));

		if ("APPROVED".equalsIgnoreCase(status)) {
			user.approveSignup();
			;
		} else if ("REJECTED".equalsIgnoreCase(status)) {
			user.rejectSignup();
		} else {
			throw new IllegalArgumentException("지원하지 않는 상태값입니다 : " + status);
		}

		userRepository.save(user);
	}

}
