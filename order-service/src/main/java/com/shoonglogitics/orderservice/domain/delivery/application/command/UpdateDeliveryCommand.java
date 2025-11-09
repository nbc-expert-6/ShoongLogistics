package com.shoonglogitics.orderservice.domain.delivery.application.command;

import java.util.UUID;

import com.shoonglogitics.orderservice.domain.delivery.presentation.dto.UpdateDeliveryRequest;
import com.shoonglogitics.orderservice.global.common.vo.AuthUser;
import com.shoonglogitics.orderservice.global.common.vo.UserRoleType;

public record UpdateDeliveryCommand(
	UUID deliveryId,
	Long userId,
	UserRoleType role,
	String request,
	UUID shipperId,
	String shipperName,
	String shipperPhoneNumber,
	String shipperSlackId
) {
	public static UpdateDeliveryCommand from(
		UUID deliveryId, UpdateDeliveryRequest request, AuthUser authUser) {
		return new UpdateDeliveryCommand(
			deliveryId,
			authUser.getUserId(),
			authUser.getRole(),
			request.request(),
			request.shipperId(),
			request.shipperName(),
			request.shipperPhoneNumber(),
			request.shipperSlackId()
		);
	}
}
