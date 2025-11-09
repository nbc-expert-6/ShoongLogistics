package com.shoonglogitics.orderservice.domain.order.infrastructure.external.dto;

public record FeignUpdateDeliveryRequest(
	String request
) {
	public static FeignUpdateDeliveryRequest from(String request) {
		return new FeignUpdateDeliveryRequest(request);
	}
}
