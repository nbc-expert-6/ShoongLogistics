package com.shoonglogitics.userservice.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoonglogitics.userservice.application.command.LoginUserCommand;
import com.shoonglogitics.userservice.application.command.SignUpUserCommand;
import com.shoonglogitics.userservice.application.command.UpdateUserCommand;
import com.shoonglogitics.userservice.application.dto.HubManagerViewResponseDto;
import com.shoonglogitics.userservice.application.dto.LoginUserResponseDto;
import com.shoonglogitics.userservice.application.dto.PageResponse;
import com.shoonglogitics.userservice.application.dto.ShipperViewResponseDto;
import com.shoonglogitics.userservice.application.strategy.signup.SignUpStrategy;
import com.shoonglogitics.userservice.application.strategy.view.CompanyManagerViewStrategy;
import com.shoonglogitics.userservice.application.strategy.view.HubManagerViewStrategy;
import com.shoonglogitics.userservice.application.strategy.view.MasterViewStrategy;
import com.shoonglogitics.userservice.application.strategy.view.ShipperViewStrategy;
import com.shoonglogitics.userservice.application.strategy.view.UserViewStrategy;
import com.shoonglogitics.userservice.domain.entity.SignupStatus;
import com.shoonglogitics.userservice.domain.entity.User;
import com.shoonglogitics.userservice.domain.entity.UserUpdatable;
import com.shoonglogitics.userservice.domain.repository.UserRepository;
import com.shoonglogitics.userservice.domain.vo.Email;
import com.shoonglogitics.userservice.domain.vo.Name;
import com.shoonglogitics.userservice.domain.vo.PhoneNumber;
import com.shoonglogitics.userservice.domain.vo.SlackId;
import com.shoonglogitics.userservice.presentation.dto.request.PageSizeType;
import com.shoonglogitics.userservice.security.JwtProvider;

import io.micrometer.core.annotation.Timed;

@Service
@Timed(value = "my.user")
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
	public <T> PageResponse<T> getUsers(String roleKey,
		int page,
		PageSizeType pageSizeType,
		List<String> sortBy,
		List<String> direction,
		UUID hubId) {
		UserViewStrategy<T> strategy = (UserViewStrategy<T>)userViewStrategyMap.get(roleKey);

		if (strategy == null) {
			throw new IllegalArgumentException("지원하지 않는 역할입니다 : " + roleKey);
		}

		List<Sort.Order> orders = new ArrayList<>();
		for (int i = 0; i < sortBy.size(); i++) {
			String sortField = sortBy.get(i);
			String sortDirection = (i < direction.size()) ? direction.get(i) : "DESC";
			Sort.Order order = sortDirection.equalsIgnoreCase("ASC") ?
				Sort.Order.asc(sortField) :
				Sort.Order.desc(sortField);
			orders.add(order);
		}
		Sort sort = Sort.by(orders);

		// Pageable 생성
		Pageable pageable = PageRequest.of(page, pageSizeType.getValue(), sort);

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

	private boolean canHubManager(Long requesterId, Long targetUserId) {
		// 허브 관리자 조회
		HubManagerViewResponseDto manager = userRepository.findHubManagerById(requesterId)
			.orElseThrow(() -> new IllegalArgumentException("허브 관리자를 찾을 수 없습니다."));

		// 대상이 허브 관리자인지 확인
		Optional<HubManagerViewResponseDto> targetManagerOpt = userRepository.findHubManagerById(targetUserId);
		if (targetManagerOpt.isPresent()) {
			HubManagerViewResponseDto targetManager = targetManagerOpt.get();
			return manager.getHubId().equals(targetManager.getHubId());
		}

		// 대상이 배송 담당자인지 확인
		Optional<ShipperViewResponseDto> targetShipperOpt = userRepository.findShipperById(targetUserId);
		if (targetShipperOpt.isPresent()) {
			ShipperViewResponseDto targetShipper = targetShipperOpt.get();
			return manager.getHubId().equals(targetShipper.getHubId());
		}

		return false;
	}

	public boolean canUpdateUser(String requesterRole, Long requesterId, Long targetUserId) {
		if ("MASTER".equals(requesterRole)) {
			return true;
		}

		if ("HUB_MANAGER".equals(requesterRole)) {
			return canHubManager(requesterId, targetUserId);
		}

		return false;
	}

	// canUpdateUser랑 코드가 똑같지만
	// 추후 update, delete가 권한별로 달라질 수 있다는 확장성을 고려하여 일부러 분리
	public boolean canDeleteUser(String requesterRole, Long requesterId, Long targetUserId) {
		if ("MASTER".equals(requesterRole)) {
			return true;
		}

		if ("HUB_MANAGER".equals(requesterRole)) {
			return canHubManager(requesterId, targetUserId);
		}

		return false;
	}

	@Transactional
	public void updateUser(Long userId, UpdateUserCommand command) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다."));

		if (user instanceof UserUpdatable updatable) {
			updatable.updateUserInfo(
				new Name(command.getName()),
				new Email(command.getEmail()),
				new SlackId(command.getSlackId()),
				new PhoneNumber(command.getPhoneNumber())
			);
		} else {
			throw new IllegalArgumentException("업데이트가 불가능한 사용자 타입입니다.");
		}
	}

	@Transactional
	public void deleteUser(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new IllegalArgumentException("해당 User를 찾을 수 없습니다."));

		if (user.isDeleted()) {
			throw new IllegalArgumentException("이미 삭제된 사용자입니다.");
		}

		user.softDelete(userId);
	}

}
