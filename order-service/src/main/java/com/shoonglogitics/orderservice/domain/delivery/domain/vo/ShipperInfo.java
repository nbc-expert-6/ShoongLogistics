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
	private String shipperSlackId;

	public static ShipperInfo of(UUID shipperId, String shipperName, String shipperPhoneNumber, String shipperSlackId) {
		ShipperInfo shipperInfo = new ShipperInfo();
		shipperInfo.shipperId = shipperId;
		shipperInfo.shipperName = shipperName;
		shipperInfo.shipperPhoneNumber = shipperPhoneNumber;
		shipperInfo.shipperSlackId = shipperSlackId;
		return shipperInfo;
	}

	public void update(
		UUID shipperId,
		String shipperName,
		String shipperPhoneNumber,
		String shipperSlackId
	) {
		if (shipperId != null) {
			this.shipperId = shipperId;
		}

		if (shipperName != null) {
			this.shipperName = shipperName;
		}

		if (shipperPhoneNumber != null) {
			this.shipperPhoneNumber = shipperPhoneNumber;
		}

		if (shipperSlackId != null) {
			this.shipperSlackId = shipperSlackId;
		}

	}
}
