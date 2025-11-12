package com.shoonglogitics.orderservice.domain.delivery.application.service.dto;

import java.util.UUID;

public record RoutesInfo(
	UUID departureHubId,
	UUID arrivalHubId,
	int sequence,
	int estimateDistance,
	int estimatedDuration
) {
	public static RoutesInfo from(
		UUID departureHubId,
		UUID arrivalHubId,
		int sequence,
		int estimateDistance,
		int estimatedDuration
	) {
		return new RoutesInfo(
			departureHubId,
			arrivalHubId,
			sequence,
			estimateDistance,
			estimatedDuration
		);
	}
}
