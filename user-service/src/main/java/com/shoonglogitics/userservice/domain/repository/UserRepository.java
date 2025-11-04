package com.shoonglogitics.userservice.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shoonglogitics.userservice.application.dto.CompanyManagerViewResponseDto;
import com.shoonglogitics.userservice.application.dto.HubManagerViewResponseDto;
import com.shoonglogitics.userservice.application.dto.MasterViewResponseDto;
import com.shoonglogitics.userservice.application.dto.ShipperViewResponseDto;
import com.shoonglogitics.userservice.domain.entity.User;

public interface UserRepository {

	// TODO : AndDeletedAtIsNull도 추가하기
	Optional<User> findByUsername(String username);

	User save(User user);

	Page<MasterViewResponseDto> findMasters(Pageable pageable);

	Page<HubManagerViewResponseDto> findHubManagers(Pageable pageable);

	Page<ShipperViewResponseDto> findShippers(UUID hubId, Pageable pageable);

	Page<CompanyManagerViewResponseDto> findCompanyManagers(Pageable pageable);
}
