package com.shoonglogitics.orderservice.domain.delivery.application.dto;

import java.util.UUID;

import com.shoonglogitics.orderservice.domain.delivery.domain.entity.Delivery;

public record CreateDeliveryResult(
	UUID deliveryId,
	String maeesage
) {
	public static CreateDeliveryResult from(Delivery delivery, String message) {
		return new CreateDeliveryResult(
			delivery.getId(),
			message
		);
	}
}
