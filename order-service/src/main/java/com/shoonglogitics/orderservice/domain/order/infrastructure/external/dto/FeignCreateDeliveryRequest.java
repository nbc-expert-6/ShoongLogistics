package com.shoonglogitics.orderservice.domain.order.infrastructure.external.dto;

import java.util.UUID;

public record FeignCreateDeliveryRequest(
	UUID orderId
) {
	public static FeignCreateDeliveryRequest from(UUID orderId) {
		return new FeignCreateDeliveryRequest(orderId);
	}
}
