package com.shoonglogitics.userservice.application.strategy.view;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shoonglogitics.userservice.application.dto.HubManagerViewResponseDto;
import com.shoonglogitics.userservice.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HubManagerViewStrategy implements UserViewStrategy<HubManagerViewResponseDto> {

	private final UserRepository userRepository;

	@Override
	public Page<HubManagerViewResponseDto> findUsers(Pageable pageable, UUID hubId) {
		return userRepository.findHubManagers(pageable);
	}

	@Override
	public Optional<HubManagerViewResponseDto> findUserById(Long id) {
		return userRepository.findHubManagerById(id);
	}

}
