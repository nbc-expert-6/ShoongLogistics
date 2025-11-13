package com.shoonglogitics.orderservice.domain.delivery.application.dto;

import java.util.UUID;

public record CreateDeliveryResult(
	UUID deliveryId
) {
	public static CreateDeliveryResult from(UUID deliveryId) {
		return new CreateDeliveryResult(
			deliveryId
		);
	}
}
