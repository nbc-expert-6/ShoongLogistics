package com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto;

public record BaseUserResponse(
	Long userId,
	String userName,
	String userRole,
	String signupStatus
) {
}
