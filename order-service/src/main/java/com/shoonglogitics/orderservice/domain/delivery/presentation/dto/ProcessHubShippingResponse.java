package com.shoonglogitics.orderservice.domain.delivery.presentation.dto;

import java.util.UUID;

public record ProcessHubShippingResponse(
	UUID deliveryRouteId
) {
	public static ProcessHubShippingResponse from(UUID deliveryRouteId) {
		return new ProcessHubShippingResponse(deliveryRouteId);
	}
}
