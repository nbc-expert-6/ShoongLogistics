package com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto;

import java.util.UUID;

public record FeignHubResponse(
	Integer sequence,
	UUID departureHubId,
	UUID arrivalHubId,
	Integer distanceMeters,
	Integer durationMinutes
) {
}
