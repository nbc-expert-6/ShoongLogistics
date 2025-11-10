package com.shoonglogitics.orderservice.domain.delivery.domain.vo;

import java.util.UUID;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class HubInfo {

	private UUID hubId;

	public static HubInfo of(UUID hubId) {
		HubInfo hub = new HubInfo();
		hub.hubId = hubId;
		return hub;
	}
}
