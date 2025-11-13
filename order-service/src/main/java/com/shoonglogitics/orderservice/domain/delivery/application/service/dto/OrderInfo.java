package com.shoonglogitics.orderservice.domain.delivery.application.service.dto;

import java.util.UUID;

import com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto.FeignOrderResponse;

public record OrderInfo(
	UUID orderId,
	Long userId,
	UUID supplierCompanyId,
	UUID receiverCompanyId,
	String deliveryRequest,
	String address,
	String addressDetail,
	String zipCode,
	Double latitude,
	Double longitude
) {
	public static OrderInfo from(FeignOrderResponse response) {
		return new OrderInfo(
			response.orderId(),
			response.userId(),
			response.supplierCompanyId(),
			response.receiverCompanyId(),
			response.deliveryRequest(),
			response.address(),
			response.addressDetail(),
			response.zipCode(),
			response.latitude(),
			response.longitude()
		);
	}
}
