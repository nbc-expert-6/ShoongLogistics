package com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto;

import java.util.UUID;

public record FeignHubResponse(
	UUID departureHubId,
	UUID arrivalHubId,
	int sequence,
	Long distance,
	int duration
) {
}
