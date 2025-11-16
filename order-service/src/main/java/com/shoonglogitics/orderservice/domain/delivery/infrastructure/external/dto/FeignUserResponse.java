package com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto;

import java.util.UUID;

import com.shoonglogitics.orderservice.domain.common.vo.ShipperType;

public record FeignUserResponse(
	Long userId,
	UUID hubId,
	String name,
	String phoneNumber,
	String slackId,
	ShipperType shipperType,
	int order,
	boolean isShippingAvailable
) {
}
