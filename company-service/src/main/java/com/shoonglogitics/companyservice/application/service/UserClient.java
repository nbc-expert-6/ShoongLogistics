package com.shoonglogitics.companyservice.application.service;

import java.util.UUID;

public interface UserClient {

	/**
	 * @param userId
	 * @param hubId
	 * @return 허브 담당자 아이디와 허브 아이디가 일치하는지 여부
	 */
	boolean isHubManager(Long userId, UUID hubId);

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
		Integer order      // SHIPPER only
	) {
	}
}
