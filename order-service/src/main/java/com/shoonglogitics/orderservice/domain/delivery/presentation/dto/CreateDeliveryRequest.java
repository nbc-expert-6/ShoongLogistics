package com.shoonglogitics.orderservice.domain.delivery.presentation.dto;

import java.util.UUID;

import com.shoonglogitics.orderservice.global.common.vo.UserRoleType;

import jakarta.validation.constraints.NotNull;

public record CreateDeliveryRequest(
	@NotNull
	UUID orderId,
	@NotNull
	Long userId,
	@NotNull
	UserRoleType role
) {
}
