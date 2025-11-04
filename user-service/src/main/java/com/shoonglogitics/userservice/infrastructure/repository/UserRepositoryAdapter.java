package com.shoonglogitics.userservice.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.shoonglogitics.userservice.application.dto.CompanyManagerViewResponseDto;
import com.shoonglogitics.userservice.application.dto.HubManagerViewResponseDto;
import com.shoonglogitics.userservice.application.dto.MasterViewResponseDto;
import com.shoonglogitics.userservice.application.dto.ShipperViewResponseDto;
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

	@Override
	public Page<MasterViewResponseDto> findMasters(Pageable pageable) {
		return jpaUserRepository.findMasters(pageable);
	}

	@Override
	public Page<HubManagerViewResponseDto> findHubManagers(Pageable pageable) {
		return jpaUserRepository.findHubManagers(pageable);
	}

	@Override
	public Page<ShipperViewResponseDto> findShippers(UUID hubId, Pageable pageable) {
		if (hubId != null) {
			return jpaUserRepository.findShippersByHubId(hubId, pageable);
		} else {
			return jpaUserRepository.findAllShippers(pageable);
		}
	}

	@Override
	public Page<CompanyManagerViewResponseDto> findCompanyManagers(Pageable pageable) {
		return jpaUserRepository.findCompanyManagers(pageable);
	}

}
