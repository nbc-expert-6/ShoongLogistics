package com.shoonglogitics.userservice.application.strategy.view;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shoonglogitics.userservice.application.dto.MasterViewResponseDto;
import com.shoonglogitics.userservice.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MasterViewStrategy implements UserViewStrategy<MasterViewResponseDto> {

	private final UserRepository userRepository;

	@Override
	public Page<MasterViewResponseDto> findUsers(Pageable pageable, UUID hubId) {
		return userRepository.findMasters(pageable);
	}

}
