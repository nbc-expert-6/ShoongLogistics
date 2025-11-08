package com.shoonglogitics.hubservice.presentation.dto.response;

import java.util.UUID;

import com.shoonglogitics.hubservice.application.dto.HubResult;

public record HubDetailResponse(
	UUID hubId,
	String name,
	String address,
	Double latitude,
	Double longitude,
	String hubType
) {

	public static HubDetailResponse from(HubResult hub) {
		return new HubDetailResponse(
			hub.hubId(),
			hub.name(),
			hub.address(),
			hub.latitude(),
			hub.longitude(),
			hub.hubType()
		);
	}
}
