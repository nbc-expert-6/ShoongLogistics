package com.shoonglogitics.orderservice.domain.delivery.application.command;

import java.util.UUID;

import com.shoonglogitics.orderservice.global.common.vo.UserRoleType;

public record DeleteDeliveryCommand(
	UUID orderId,
	Long userId,
	UserRoleType role
) {
	public static DeleteDeliveryCommand from(
		UUID orderId,
		Long userId,
		UserRoleType role
	) {
		return new DeleteDeliveryCommand(
			orderId, userId, role);
	}
}
