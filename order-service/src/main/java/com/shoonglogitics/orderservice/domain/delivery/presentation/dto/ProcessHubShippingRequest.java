package com.shoonglogitics.orderservice.domain.delivery.presentation.dto;

public record ProcessHubShippingRequest(
	Boolean isDeparture,
	Integer distance,
	Integer duration
) {

}
