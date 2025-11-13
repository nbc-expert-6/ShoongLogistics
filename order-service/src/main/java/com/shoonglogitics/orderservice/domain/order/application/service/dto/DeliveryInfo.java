package com.shoonglogitics.orderservice.domain.order.application.service.dto;

import java.util.UUID;

public record DeliveryInfo(
	UUID deliveryId
) {
	public static DeliveryInfo from(UUID deliveryId) {
		return new DeliveryInfo(deliveryId);
	}
}
