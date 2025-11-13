package com.shoonglogitics.companyservice.infrastructure.external.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserInfoFeignClientResponse(
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
