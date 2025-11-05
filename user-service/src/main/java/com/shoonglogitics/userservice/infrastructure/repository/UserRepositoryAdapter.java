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
	public Optional<User> findByUserName(String userName) {
		return jpaUserRepository.findByUserName(userName);
	}

	@Override
	public Optional<User> findById(Long id) {
		return jpaUserRepository.findById(id);
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
	public Page<ShipperViewResponseDto> findShippers(Pageable pageable) {
		return jpaUserRepository.findAllShippers(pageable);
	}

	@Override
	public Page<ShipperViewResponseDto> findShippersByHubId(UUID hubId, Pageable pageable) {
		return jpaUserRepository.findShippersByHubId(hubId, pageable);
	}

	@Override
	public Page<CompanyManagerViewResponseDto> findCompanyManagers(Pageable pageable) {
		return jpaUserRepository.findCompanyManagers(pageable);
	}

	@Override
	public Optional<MasterViewResponseDto> findMasterById(Long id) {
		return jpaUserRepository.findMasterById(id);
	}

	@Override
	public Optional<HubManagerViewResponseDto> findHubManagerById(Long id) {
		return jpaUserRepository.findHubManagerById(id);
	}

	@Override
	public Optional<ShipperViewResponseDto> findShipperById(Long id) {
		return jpaUserRepository.findShipperById(id);
	}

	@Override
	public Optional<CompanyManagerViewResponseDto> findCompanyManagerById(Long id) {
		return jpaUserRepository.findCompanyManagerById(id);
	}

}
