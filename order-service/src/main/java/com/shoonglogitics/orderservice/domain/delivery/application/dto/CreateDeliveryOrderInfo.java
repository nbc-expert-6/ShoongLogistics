package com.shoonglogitics.orderservice.domain.delivery.application.dto;

import java.util.UUID;

import com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto.FeignOrderResponse;

public record CreateDeliveryOrderInfo(
	UUID orderId,
	UUID supplierCompanyId,
	UUID receiverCompanyId,
	String deliveryRequest,
	String address,
	String addressDetail,
	String zipCode,
	Double latitude,
	Double longitude
) {
	public static CreateDeliveryOrderInfo from(FeignOrderResponse response) {
		return new CreateDeliveryOrderInfo(
			response.orderId(),
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
