package com.shoonglogitics.orderservice.domain.delivery.presentation.dto;

import java.util.UUID;

import com.shoonglogitics.orderservice.domain.common.vo.AuthUser;

public record ProcessHubShippingCommand(
	UUID deliveryId,
	UUID deliveryRouteId,
	Boolean isDeparture,
	Integer distance,
	Integer duration,
	AuthUser authUser
) {
	public static ProcessHubShippingCommand from(
		UUID deliveryId,
		UUID deliveryRouteId,
		Boolean isDeparture,
		Integer distance,
		Integer duration,
		AuthUser authUser
	) {
		return new ProcessHubShippingCommand(
			deliveryId,
			deliveryRouteId,
			isDeparture,
			distance,
			duration,
			authUser
		);
	}
}
