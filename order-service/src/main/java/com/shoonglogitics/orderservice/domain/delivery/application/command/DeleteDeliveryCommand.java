package com.shoonglogitics.orderservice.domain.delivery.application.command;

import java.util.UUID;

import com.shoonglogitics.orderservice.domain.common.vo.UserRoleType;

public record DeleteDeliveryCommand(
	UUID deliveryId,
	Long userId,
	UserRoleType role
) {
	public static DeleteDeliveryCommand from(
		UUID deliveryId,
		Long userId,
		UserRoleType role
	) {
		return new DeleteDeliveryCommand(
			deliveryId, userId, role);
	}
}
