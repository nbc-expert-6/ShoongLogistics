package com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record FeignCompanyResponse(
	UUID id,
	UUID hubId,
	String name,
	String address,
	String addressDetail,
	String zipCode,
	Double latitude,
	Double longitude,
	String type,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {

}
