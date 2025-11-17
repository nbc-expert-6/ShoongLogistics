package com.shoonglogitics.orderservice.domain.delivery.application.service.dto;

import java.util.UUID;

import com.shoonglogitics.orderservice.domain.common.vo.ShipperType;

public record ShipperInfo(
	Long shipperId,
	UUID hubId,
	String shipperName,
	String shipperPhoneNumber,
	String slackId,
	ShipperType type,
	int order,
	boolean isShippingAvailable
) {
	public static ShipperInfo from(
		Long shipperId,
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
