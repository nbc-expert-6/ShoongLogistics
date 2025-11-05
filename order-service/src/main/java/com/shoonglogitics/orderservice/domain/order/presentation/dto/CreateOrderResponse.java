package com.shoonglogitics.orderservice.domain.order.presentation.dto;

import java.util.UUID;

public record CreateOrderResponse(
	UUID orderId,
	String message
) {
}
