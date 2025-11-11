package com.shoonglogitics.hubservice.presentation.dto.response;

import java.io.Serializable;
import java.util.UUID;

import com.shoonglogitics.hubservice.application.dto.HubResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HubDetailResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private UUID hubId;
	private String name;
	private String address;
	private Double latitude;
	private Double longitude;
	private String hubType;

	public static HubDetailResponse from(HubResult hub) {
		return HubDetailResponse.builder()
				.hubId(hub.getHubId())
				.name(hub.getName())
				.address(hub.getAddress())
				.latitude(hub.getLatitude())
				.longitude(hub.getLongitude())
				.hubType(hub.getHubType())
				.build();
	}
}
