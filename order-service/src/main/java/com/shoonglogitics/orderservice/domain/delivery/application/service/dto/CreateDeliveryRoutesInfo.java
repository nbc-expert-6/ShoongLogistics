package com.shoonglogitics.orderservice.domain.delivery.application.service.dto;

import java.util.UUID;

public record CreateDeliveryRoutesInfo(
	UUID departureHubId,
	UUID arrivalHubId,
	int sequence,
	Long estimateDistance,
	int estimatedDuration
) {
	public static CreateDeliveryRoutesInfo from(
		UUID departureHubId,
		UUID arrivalHubId,
		int sequence,
		Long estimateDistance,
		int estimatedDuration
	) {
		return new CreateDeliveryRoutesInfo(
			departureHubId,
			arrivalHubId,
			sequence,
			estimateDistance,
			estimatedDuration
		);
	}
}
