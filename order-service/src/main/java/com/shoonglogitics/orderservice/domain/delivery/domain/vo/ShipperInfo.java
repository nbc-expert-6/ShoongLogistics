package com.shoonglogitics.orderservice.domain.delivery.domain.vo;

import java.util.UUID;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ShipperInfo {
	private UUID shipperId;
	private String shipperName;
	private String shipperPhoneNumber;
	private String slackId;

	public static ShipperInfo of(UUID shipperId, String shipperName, String shipperPhoneNumber, String slackId) {
		ShipperInfo shipperInfo = new ShipperInfo();
		shipperInfo.shipperId = shipperId;
		shipperInfo.shipperName = shipperName;
		shipperInfo.shipperPhoneNumber = shipperPhoneNumber;
		shipperInfo.slackId = slackId;
		return shipperInfo;
	}
}
