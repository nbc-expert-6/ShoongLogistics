package com.shoonglogitics.orderservice.domain.order.presentation.dto;

public record UpdateOrderRequest(
	String request,
	String deliveryRequest
) {
}
