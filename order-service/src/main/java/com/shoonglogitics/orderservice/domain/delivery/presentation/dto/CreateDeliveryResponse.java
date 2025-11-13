package com.shoonglogitics.orderservice.domain.delivery.presentation.dto;

import java.util.UUID;

import com.shoonglogitics.orderservice.domain.delivery.application.dto.CreateDeliveryResult;

public record CreateDeliveryResponse(
	UUID deliveryId
) {
	public static CreateDeliveryResponse from(CreateDeliveryResult result) {
		return new CreateDeliveryResponse(
			result.deliveryId()
		);
	}
}
