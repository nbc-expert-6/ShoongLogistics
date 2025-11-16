package com.shoonglogitics.orderservice.domain.order.application.command;

import java.util.UUID;

import com.shoonglogitics.orderservice.domain.common.vo.AuthUser;
import com.shoonglogitics.orderservice.domain.common.vo.UserRoleType;

public record DeleteOrderCommand(
	UUID orderId,
	Long userId,
	UserRoleType role
) {
	public static DeleteOrderCommand from(UUID orderId, AuthUser authUser) {
		return new DeleteOrderCommand(
			orderId,
			authUser.getUserId(),
			authUser.getRole()
		);
	}
}
