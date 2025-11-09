package com.shoonglogitics.orderservice.domain.order.presentation.dto;

import java.util.UUID;

public record DeleteOrderResponse(
	UUID orderId
) {
	public static DeleteOrderResponse from(UUID orderId) {
		return new DeleteOrderResponse(orderId);
	}
}
