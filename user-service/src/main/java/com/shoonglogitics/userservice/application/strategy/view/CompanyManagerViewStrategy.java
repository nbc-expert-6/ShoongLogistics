package com.shoonglogitics.userservice.application.strategy.view;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shoonglogitics.userservice.application.dto.CompanyManagerViewResponseDto;
import com.shoonglogitics.userservice.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyManagerViewStrategy implements UserViewStrategy<CompanyManagerViewResponseDto> {

	private final UserRepository userRepository;

	@Override
	public Page<CompanyManagerViewResponseDto> findUsers(Pageable pageable, UUID hubId) {
		return userRepository.findCompanyManagers(pageable);
	}

	@Override
	public Optional<CompanyManagerViewResponseDto> findUserById(Long id) {
		return userRepository.findCompanyManagerById(id);
	}

}
