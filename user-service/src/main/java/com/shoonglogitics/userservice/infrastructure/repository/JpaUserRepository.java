package com.shoonglogitics.userservice.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shoonglogitics.userservice.application.dto.CompanyManagerViewResponseDto;
import com.shoonglogitics.userservice.application.dto.HubManagerViewResponseDto;
import com.shoonglogitics.userservice.application.dto.MasterViewResponseDto;
import com.shoonglogitics.userservice.application.dto.ShipperViewResponseDto;
import com.shoonglogitics.userservice.domain.entity.CompanyManager;
import com.shoonglogitics.userservice.domain.entity.HubManager;
import com.shoonglogitics.userservice.domain.entity.Shipper;
import com.shoonglogitics.userservice.domain.entity.User;
import com.shoonglogitics.userservice.domain.vo.HubId;

@Repository
public interface JpaUserRepository extends JpaRepository<User, Long> {

	// TODO : AndDeletedAtIsNull도 추가하기
	Optional<User> findByUserName(String userName);

	@Query("""
		    select new com.shoonglogitics.userservice.application.dto.MasterViewResponseDto(
		        m.id,
		        m.name.value,
		        m.email.value,
		        m.phoneNumber.value,
		        m.slackId.value
		    )
		    from Master m
		""")
	Page<MasterViewResponseDto> findMasters(Pageable pageable);

	@Query("""
		    select new com.shoonglogitics.userservice.application.dto.HubManagerViewResponseDto(
		        h.id,
		        h.name.value,
		        h.email.value,
		        h.phoneNumber.value,
		        h.slackId.value,
		        h.hubId.id
		    )
		    from HubManager h
		""")
	Page<HubManagerViewResponseDto> findHubManagers(Pageable pageable);

	@Query("""
		    select new com.shoonglogitics.userservice.application.dto.ShipperViewResponseDto(
		        s.id,
		        s.name.value,
		        s.email.value,
		        s.slackId.value,
		        s.phoneNumber.value,
		        s.hubId.id, s.shipperType, s.order, s.isShippingAvailable
		    )
		    from Shipper s
		    where s.hubId.id = :hubId
		""")
	Page<ShipperViewResponseDto> findShippersByHubId(UUID hubId, Pageable pageable);

	@Query("""
		    select new com.shoonglogitics.userservice.application.dto.ShipperViewResponseDto(
		        s.id,
		        s.name.value,
		        s.email.value,
		        s.slackId.value,
		        s.phoneNumber.value,
		        s.hubId.id, s.shipperType, s.order, s.isShippingAvailable
		    )
		    from Shipper s
		""")
	Page<ShipperViewResponseDto> findAllShippers(Pageable pageable);

	@Query("""
		    select new com.shoonglogitics.userservice.application.dto.CompanyManagerViewResponseDto(
		        c.id,
		        c.name.value,
		        c.email.value,
		        c.slackId.value,
		        c.phoneNumber.value,
		        c.companyId.id
		    )
		    from CompanyManager c
		""")
	Page<CompanyManagerViewResponseDto> findCompanyManagers(Pageable pageable);

	@Query("""
		    select new com.shoonglogitics.userservice.application.dto.MasterViewResponseDto(
		        m.id,
		        m.name.value,
		        m.email.value,
		        m.phoneNumber.value,
		        m.slackId.value
		    )
		    from Master m
		    where m.id = :id
		""")
	Optional<MasterViewResponseDto> findMasterById(@Param("id") Long id);

	@Query("""
		    select new com.shoonglogitics.userservice.application.dto.HubManagerViewResponseDto(
		        h.id,
		        h.name.value,
		        h.email.value,
		        h.phoneNumber.value,
		        h.slackId.value,
		        h.hubId.id
		    )
		    from HubManager h
				    where h.id = :id
		""")
	Optional<HubManagerViewResponseDto> findHubManagerById(@Param("id") Long id);

	@Query("""
		    select new com.shoonglogitics.userservice.application.dto.ShipperViewResponseDto(
		        s.id,
		        s.name.value,
		        s.email.value,
		        s.slackId.value,
		        s.phoneNumber.value,
		        s.hubId.id, s.shipperType, s.order, s.isShippingAvailable
		    )
		    from Shipper s where s.id = :id
		""")
	Optional<ShipperViewResponseDto> findShipperById(@Param("id") Long id);

	@Query("""
		    select new com.shoonglogitics.userservice.application.dto.CompanyManagerViewResponseDto(
		        c.id,
		        c.name.value,
		        c.email.value,
		        c.slackId.value,
		        c.phoneNumber.value,
		        c.companyId.id
		    )
		    from CompanyManager c where c.id = :id
		""")
	Optional<CompanyManagerViewResponseDto> findCompanyManagerById(@Param("id") Long id);

	@Query("SELECT MAX(s.order) FROM Shipper s WHERE s.hubId = :hubId")
	Optional<Integer> findLastShipperOrderByHubId(@Param("hubId") HubId hubId);

	@Query("SELECT u FROM HubManager u WHERE u.hubId.id = :hubId AND u.deletedAt IS NULL")
	List<HubManager> findHubManagersByHubId(@Param("hubId") UUID hubId);

	@Query("SELECT u FROM Shipper u WHERE u.hubId.id = :hubId AND u.deletedAt IS NULL")
	List<Shipper> findShippersByHubId(@Param("hubId") UUID hubId);

	@Query("SELECT u FROM CompanyManager u WHERE u.companyId.id = :companyId AND u.deletedAt IS NULL")
	List<CompanyManager> findCompanyManagersByCompanyId(@Param("companyId") UUID companyId);

}
