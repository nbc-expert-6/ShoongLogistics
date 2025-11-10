package com.shoonglogitics.hubservice.application.command;

import com.shoonglogitics.hubservice.domain.vo.HubType;

public record CreateHubCommand(
	String name,
	String address,
	Double latitude,
	Double longitude,
	HubType hubType
) {
}
