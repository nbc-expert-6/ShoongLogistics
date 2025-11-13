package com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto;

import java.util.UUID;

public record ListShippingRoutesRequest(
	UUID departureHubId,
	UUID destinationHubId
) {
	public static ListShippingRoutesRequest from(UUID departureHubId, UUID destinationHubId) {
		return new ListShippingRoutesRequest(departureHubId, destinationHubId);
	}
}
