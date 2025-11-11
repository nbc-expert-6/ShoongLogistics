package com.shoonglogitics.orderservice.domain.delivery.presentation.dto;

import java.util.UUID;

public record ProcessHubShippingRequest(
	UUID deliveryId,
	UUID deliveryRouteId,
	Boolean isDeparture,
	Long distance,
	Integer duration
) {

}
