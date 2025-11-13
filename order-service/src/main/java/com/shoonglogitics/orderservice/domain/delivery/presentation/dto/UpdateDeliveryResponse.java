package com.shoonglogitics.orderservice.domain.delivery.presentation.dto;

import java.util.UUID;

public record UpdateDeliveryResponse(
	UUID deliveryId
) {
	public static UpdateDeliveryResponse from(UUID deliveryId) {
		return new UpdateDeliveryResponse(deliveryId);
	}
}
