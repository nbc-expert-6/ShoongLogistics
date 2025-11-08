package com.shoonglogitics.orderservice.domain.delivery.application.service.dto;

import java.util.UUID;

import com.shoonglogitics.orderservice.global.common.vo.ShipperType;

public record CreateDeliveryShipperInfo(
	UUID shipperId,
	UUID hubId,
	String shipperName,
	String shipperPhoneNumber,
	String slackId,
	ShipperType type,
	int order,
	boolean isShippingAvailable
) {
	public static CreateDeliveryShipperInfo from(
		UUID shipperId,
		UUID hubId,
		String shipperName,
		String shipperPhoneNumber,
		String slackId,
		ShipperType type,
		int order,
		boolean isShippingAvailable
	) {
		return new CreateDeliveryShipperInfo(
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
