package com.shoonglogitics.hubservice.presentation.dto.response;

import java.util.List;

import com.shoonglogitics.hubservice.application.dto.HubSummary;

public record HubListResponse(
	List<HubSummary> hubs
) {

	public static HubListResponse of(List<HubSummary> hubs) {
		return new HubListResponse(hubs);
	}
}
