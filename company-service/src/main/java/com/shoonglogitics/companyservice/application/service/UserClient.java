package com.shoonglogitics.companyservice.application.service;

import java.time.LocalDateTime;
import java.util.UUID;

import com.shoonglogitics.companyservice.domain.common.vo.AuthUser;

public interface UserClient {

	/**
	 * @param authUser
	 * @param hubId
	 * @return 허브 담당자의 아이디와 매개변수의 허브 아이디가 일치하는지 여부
	 */
	boolean isHubManager(AuthUser authUser, UUID hubId);

	/**
	 * @param authUser
	 * @param companyId
	 * @return 업체 담당자의 업체 아이디와 매개변수의 업체 아이디가 일치하는지 여부
	 */
	boolean isCompanyManager(AuthUser authUser, UUID companyId);

	/**
	 * @param companyId 업체 ID
	 * @return 업체 담당자 삭제 요청 성공 여부
	 */
	boolean deleteCompanyManager(AuthUser authUser, UUID companyId);

	/**
	 * User Service 응답 정보
	 * 역할에 따라 일부 필드는 null일 수 있음
	 */
	record UserInfo(
		Long userId,
		String userName,
		String name,
		String email,
		String phone,
		String slackId,
		String role,
		UUID hubId,        // HUB_MANAGER, COMPANY_MANAGER, SHIPPER
		UUID companyId,    // COMPANY_MANAGER, SHIPPER
		UUID shipperId,    // SHIPPER only
		String shipperType, // SHIPPER only
		Integer order,// SHIPPER only
		LocalDateTime deleteAt,
		Long deleteBy
	) {
	}
}
