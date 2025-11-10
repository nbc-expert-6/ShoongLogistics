package com.shoonglogitics.hubservice.application.dto;

import java.util.UUID;

import com.shoonglogitics.hubservice.domain.entity.Hub;

public record HubResult(UUID hubId,
						String name,
						String address,
						Double latitude,
						Double longitude,
						String hubType) {

	public static HubResult from(Hub hub) {
		return new HubResult(
			hub.getId(),
			hub.getName(),
			hub.getAddress().getValue(),
			hub.getLatitude(),
			hub.getLongitude(),
			hub.getHubType().name()
		);
	}
}
