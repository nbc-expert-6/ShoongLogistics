package com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record FeignOrderResponse(
	UUID orderId,
	Long userId,
	UUID receiverCompanyId,
	String receiverCompanyName,
	UUID supplierCompanyId,
	String supplierCompanyName,
	String request,
	String deliveryRequest,
	String address,
	String addressDetail,
	String zipCode,
	Double latitude,
	Double longitude,
	String status,
	LocalDateTime createdAt,
	LocalDateTime paidAt
) {
}
