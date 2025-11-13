package com.shoonglogitics.userservice.application.strategy.view;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shoonglogitics.userservice.application.dto.ShipperViewResponseDto;
import com.shoonglogitics.userservice.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShipperViewStrategy implements UserViewStrategy<ShipperViewResponseDto> {

	private final UserRepository userRepository;

	@Override
	public Page<ShipperViewResponseDto> findUsers(Pageable pageable, UUID hubId) {
		if (hubId != null) {
			return userRepository.findShippersByHubId(hubId, pageable);
		}
		return userRepository.findShippers(pageable);
	}

	@Override
	public Optional<ShipperViewResponseDto> findUserById(Long id) {
		return userRepository.findShipperById(id);
	}

}
