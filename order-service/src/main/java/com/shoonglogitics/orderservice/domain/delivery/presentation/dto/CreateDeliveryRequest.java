package com.shoonglogitics.orderservice.domain.delivery.presentation.dto;

import java.util.UUID;

import com.shoonglogitics.orderservice.global.common.vo.UserRoleType;

public record CreateDeliveryRequest(
	UUID orderId,
	Long userId,
	UserRoleType role
) {
}
