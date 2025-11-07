package com.shoonglogitics.orderservice.domain.delivery.application.command;

import java.util.UUID;

import com.shoonglogitics.orderservice.domain.delivery.presentation.dto.CreateDeliveryRequest;

public record CreateDeliveryCommand(
	UUID orderId
) {
	public static CreateDeliveryCommand from(CreateDeliveryRequest request) {
		return new CreateDeliveryCommand(
			request.orderID()
		);
	}
}
