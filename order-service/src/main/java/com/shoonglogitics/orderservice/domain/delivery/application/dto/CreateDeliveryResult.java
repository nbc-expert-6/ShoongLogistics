package com.shoonglogitics.orderservice.domain.delivery.application.dto;

import java.util.UUID;

public record CreateDeliveryResult(
	UUID deliveryId,
	String maeesage
) {
	public static CreateDeliveryResult from(UUID deliveryId, String message) {
		return new CreateDeliveryResult(
			deliveryId,
			message
		);
	}
}
