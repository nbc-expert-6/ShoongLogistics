package com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto;

import java.util.List;

public record FeignRouteResponse(
	List<FeignHubResponse> routes
) {
}
