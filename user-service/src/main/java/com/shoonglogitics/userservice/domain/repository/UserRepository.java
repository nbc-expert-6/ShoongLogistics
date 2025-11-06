package com.shoonglogitics.userservice.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shoonglogitics.userservice.application.dto.CompanyManagerViewResponseDto;
import com.shoonglogitics.userservice.application.dto.HubManagerViewResponseDto;
import com.shoonglogitics.userservice.application.dto.MasterViewResponseDto;
import com.shoonglogitics.userservice.application.dto.ShipperViewResponseDto;
import com.shoonglogitics.userservice.domain.entity.CompanyManager;
import com.shoonglogitics.userservice.domain.entity.HubManager;
import com.shoonglogitics.userservice.domain.entity.Shipper;
import com.shoonglogitics.userservice.domain.entity.User;
import com.shoonglogitics.userservice.domain.vo.HubId;

public interface UserRepository {

	// TODO : AndDeletedAtIsNull도 추가하기
	Optional<User> findByUsername(String username);

	User save(User user);

	Optional<User> findByUserName(String userName);

	Optional<User> findById(Long id);

	Page<MasterViewResponseDto> findMasters(Pageable pageable);

	Page<HubManagerViewResponseDto> findHubManagers(Pageable pageable);

	Page<ShipperViewResponseDto> findShippers(Pageable pageable);

	Page<ShipperViewResponseDto> findShippersByHubId(UUID hubId, Pageable pageable);

	Page<CompanyManagerViewResponseDto> findCompanyManagers(Pageable pageable);

	Optional<MasterViewResponseDto> findMasterById(Long id);

	Optional<HubManagerViewResponseDto> findHubManagerById(Long id);

	Optional<ShipperViewResponseDto> findShipperById(Long id);

	Optional<CompanyManagerViewResponseDto> findCompanyManagerById(Long id);

	Optional<Integer> findLastShipperOrderByHubId(HubId hubId);

	List<HubManager> findHubManagersByHubId(UUID hubId);

	List<Shipper> findShippersByHubId(UUID hubId);

	List<CompanyManager> findCompanyManagersByCompanyId(UUID companyId);
}
