package com.shoonglogitics.orderservice.domain.delivery.application.command;

import java.util.UUID;

import com.shoonglogitics.orderservice.domain.delivery.presentation.dto.CreateDeliveryRequest;
import com.shoonglogitics.orderservice.domain.common.vo.UserRoleType;

public record CreateDeliveryCommand(
	UUID orderId,
	Long userId,
	UserRoleType role
) {
	public static CreateDeliveryCommand from(CreateDeliveryRequest request, Long userId, UserRoleType role) {
		return new CreateDeliveryCommand(
			request.orderId(),
			userId,
			role
		);
	}
}
