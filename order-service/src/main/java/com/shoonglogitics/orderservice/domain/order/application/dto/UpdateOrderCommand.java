package com.shoonglogitics.orderservice.domain.order.application.dto;

import java.util.UUID;

import com.shoonglogitics.orderservice.global.common.vo.AuthUser;
import com.shoonglogitics.orderservice.global.common.vo.UserRoleType;

public record UpdateOrderCommand(
	String request,
	String deliveryRequest,
	Long userId,
	UserRoleType role,
	UUID orderId
) {
	public static UpdateOrderCommand from(String request, String deliveryRequest, AuthUser authUser, UUID orderId) {
		return new UpdateOrderCommand(
			request,
			deliveryRequest,
			authUser.getUserId(),
			authUser.getRole(),
			orderId
		);
	}
}
