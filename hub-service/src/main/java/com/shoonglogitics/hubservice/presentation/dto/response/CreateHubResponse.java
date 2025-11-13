package com.shoonglogitics.hubservice.presentation.dto.response;

import java.util.UUID;

import com.shoonglogitics.hubservice.domain.entity.Hub;

import lombok.Builder;

@Builder
public record CreateHubResponse(
	UUID hubId,
	String name,
	String address,
	Double latitude,
	Double longitude
) {
	public static CreateHubResponse from(Hub hub) {
		return CreateHubResponse.builder()
			.hubId(hub.getId())
			.name(hub.getName())
			.address(hub.getAddress().getValue())
			.latitude(hub.getLatitude())
			.longitude(hub.getLongitude())
			.build();
	}

}
