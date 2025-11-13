package com.shoonglogitics.hubservice.application.dto;

import java.io.Serializable;
import java.util.UUID;

import com.shoonglogitics.hubservice.domain.entity.Hub;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HubResult implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID hubId;
	private String name;
	private String address;
	private Double latitude;
	private Double longitude;
	private String hubType;

	public static HubResult from(Hub hub) {
		return HubResult.builder()
				.hubId(hub.getId())
				.name(hub.getName())
				.address(hub.getAddress().getValue())
				.latitude(hub.getLatitude())
				.longitude(hub.getLongitude())
				.hubType(hub.getHubType().name())
				.build();
	}
}