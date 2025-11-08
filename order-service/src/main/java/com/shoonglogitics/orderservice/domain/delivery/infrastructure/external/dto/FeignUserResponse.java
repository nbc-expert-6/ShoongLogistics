package com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto;

import java.util.UUID;

import com.shoonglogitics.orderservice.global.common.vo.ShipperType;

public record FeignUserResponse(
	UUID shipperId,
	UUID hubId,
	String shipperName,
	String shipperPhoneNumber,
	String slackId,
	ShipperType type,
	int order,
	boolean isShippingAvailable
) {
}
