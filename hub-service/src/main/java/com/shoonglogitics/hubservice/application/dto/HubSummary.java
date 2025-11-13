package com.shoonglogitics.hubservice.application.dto;

import com.shoonglogitics.hubservice.domain.entity.Hub;

public record HubSummary(
	String name,
	String address,
	String hubType
) {
	public static HubSummary from(Hub hub) {
		return new HubSummary(
			hub.getName(),
			hub.getAddress().getValue(),
			hub.getHubType().name()
		);
	}
}
