package com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto;

import java.util.List;
import java.util.UUID;

public record FeignRouteResponse(
	UUID startHubId,
	String startHubName,
	UUID endHubId,
	String endHubName,
	int totalDistanceMeters,
	int totalDurationMinutes,
	List<FeignWaypointResponse> waypoints,
	List<FeignHubResponse> routes
) {
}
