package com.shoonglogitics.orderservice.domain.order.presentation.dto;

import java.util.UUID;

public record UpdateOrderResponse(
	UUID orderId
) {
	public static UpdateOrderResponse from(UUID orderId) {
		return new UpdateOrderResponse(orderId);
	}
}
