package com.shoonglogitics.orderservice.domain.delivery.application.command;

import java.util.UUID;

import com.shoonglogitics.orderservice.global.common.vo.AuthUser;

public record ProcessDeliveryCommand(
	UUID deliveryId,
	Boolean isDeparture,
	AuthUser authUser
) {
	public static ProcessDeliveryCommand from(
		UUID deliveryId,
		Boolean isDeparture,
		AuthUser authUser
	) {
		return new ProcessDeliveryCommand(deliveryId, isDeparture, authUser);
	}
}
