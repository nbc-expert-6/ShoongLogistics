package com.shoonglogitics.orderservice.domain.delivery.presentation.dto;

import java.util.UUID;

public record DeleteDeliveryResponse(
	UUID deliveryId
) {
	public static DeleteDeliveryResponse from(UUID deliveryId) {
		return new DeleteDeliveryResponse(deliveryId);
	}
}
