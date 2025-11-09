package com.shoonglogitics.orderservice.domain.delivery.application.service.dto;

import java.util.UUID;

import com.shoonglogitics.orderservice.global.common.vo.ShipperType;

public record ShipperInfo(
	UUID shipperId,
	UUID hubId,
	String shipperName,
	String shipperPhoneNumber,
	String slackId,
	ShipperType type,
	int order,
	boolean isShippingAvailable
) {
	public static ShipperInfo from(
		UUID shipperId,
		UUID hubId,
		String shipperName,
		String shipperPhoneNumber,
		String slackId,
		ShipperType type,
		int order,
		boolean isShippingAvailable
	) {
		return new ShipperInfo(
			shipperId,
			hubId,
			shipperName,
			shipperPhoneNumber,
			slackId,
			type,
			order,
			isShippingAvailable
		);
	}
}
