package com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.mapper;

import java.util.List;

import com.shoonglogitics.orderservice.domain.delivery.application.service.dto.RoutesInfo;
import com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto.FeignHubResponse;

public final class HubMapper {
	public HubMapper() {
	}

	//실제 로직
	public static List<RoutesInfo> toCreateDeliveryRoutesInfo(List<FeignHubResponse> data) {

		return data.stream()
			.map(r -> new RoutesInfo(
				r.departureHubId(),
				r.arrivalHubId(),
				r.sequence(),
				r.distanceMeters(),
				r.durationMinutes()
			))
			.toList();
	}
}
