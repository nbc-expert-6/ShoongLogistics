package com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.mapper;

import java.util.List;
import java.util.UUID;

import com.shoonglogitics.orderservice.domain.delivery.application.service.dto.CreateDeliveryShipperInfo;
import com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto.FeignUserResponse;
import com.shoonglogitics.orderservice.global.common.vo.ShipperType;

public final class UserMapper {

	public static List<CreateDeliveryShipperInfo> toCreateDeliveryShipperInfoDummy(UUID hubId) {
		List<CreateDeliveryShipperInfo> all = List.of(
			CreateDeliveryShipperInfo.from(UUID.randomUUID(),
				UUID.fromString("8a54a9c4-7e9d-4d9b-b25f-1f4b8f3e6a77"),
				"허브1_허브담당자A", "010-1111-1111", "slack-hub1A", ShipperType.HUB_SHIPPER, 1, true),
			CreateDeliveryShipperInfo.from(UUID.randomUUID(),
				UUID.fromString("8a54a9c4-7e9d-4d9b-b25f-1f4b8f3e6a77"),
				"허브1_허브담당자B", "010-1111-1112", "slack-hub1B", ShipperType.HUB_SHIPPER, 2, true),
			CreateDeliveryShipperInfo.from(UUID.randomUUID(),
				UUID.fromString("8a54a9c4-7e9d-4d9b-b25f-1f4b8f3e6a77"),
				"허브1_업체담당자A", "010-1111-1113", "slack-comp1A", ShipperType.COMPANY_SHIPPER, 1, false),
			CreateDeliveryShipperInfo.from(UUID.randomUUID(),
				UUID.fromString("8a54a9c4-7e9d-4d9b-b25f-1f4b8f3e6a77"),
				"허브1_업체담당자B", "010-1111-1114", "slack-comp1B", ShipperType.COMPANY_SHIPPER, 2, true),

			CreateDeliveryShipperInfo.from(UUID.randomUUID(),
				UUID.fromString("b19f84b1-3c56-4c9c-8f85-1b3d93e1e4b2"),
				"허브2_허브담당자A", "010-2222-1111", "slack-hub2A", ShipperType.HUB_SHIPPER, 1, false),
			CreateDeliveryShipperInfo.from(UUID.randomUUID(),
				UUID.fromString("b19f84b1-3c56-4c9c-8f85-1b3d93e1e4b2"),
				"허브2_허브담당자B", "010-2222-1112", "slack-hub2B", ShipperType.HUB_SHIPPER, 2, false),
			CreateDeliveryShipperInfo.from(UUID.randomUUID(),
				UUID.fromString("b19f84b1-3c56-4c9c-8f85-1b3d93e1e4b2"),
				"허브2_업체담당자A", "010-2222-1113", "slack-comp2A", ShipperType.COMPANY_SHIPPER, 1, true),
			CreateDeliveryShipperInfo.from(UUID.randomUUID(),
				UUID.fromString("b19f84b1-3c56-4c9c-8f85-1b3d93e1e4b2"),
				"허브2_업체담당자B", "010-2222-1114", "slack-comp2B", ShipperType.COMPANY_SHIPPER, 2, true),

			CreateDeliveryShipperInfo.from(UUID.randomUUID(),
				UUID.fromString("a72cfa9f-12b1-4b94-8e31-3c8bfe2df51d"),
				"허브3_허브담당자A", "010-3333-1111", "slack-hub3A", ShipperType.HUB_SHIPPER, 1, false),
			CreateDeliveryShipperInfo.from(UUID.randomUUID(),
				UUID.fromString("a72cfa9f-12b1-4b94-8e31-3c8bfe2df51d"),
				"허브3_허브담당자B", "010-3333-1112", "slack-hub3B", ShipperType.HUB_SHIPPER, 2, true),
			CreateDeliveryShipperInfo.from(UUID.randomUUID(),
				UUID.fromString("a72cfa9f-12b1-4b94-8e31-3c8bfe2df51d"),
				"허브3_업체담당자A", "010-3333-1113", "slack-comp3A", ShipperType.COMPANY_SHIPPER, 1, true),
			CreateDeliveryShipperInfo.from(UUID.randomUUID(),
				UUID.fromString("a72cfa9f-12b1-4b94-8e31-3c8bfe2df51d"),
				"허브3_업체담당자B", "010-3333-1114", "slack-comp3B", ShipperType.COMPANY_SHIPPER, 2, true),

			CreateDeliveryShipperInfo.from(UUID.randomUUID(),
				UUID.fromString("db73202e-4c64-42ad-b90b-4af70b2079c3"),
				"허브4_허브담당자A", "010-4444-1111", "slack-hub4A", ShipperType.HUB_SHIPPER, 1, true),
			CreateDeliveryShipperInfo.from(UUID.randomUUID(),
				UUID.fromString("db73202e-4c64-42ad-b90b-4af70b2079c3"),
				"허브4_허브담당자B", "010-4444-1112", "slack-hub4B", ShipperType.HUB_SHIPPER, 2, true),
			CreateDeliveryShipperInfo.from(UUID.randomUUID(),
				UUID.fromString("db73202e-4c64-42ad-b90b-4af70b2079c3"),
				"허브4_업체담당자A", "010-4444-1113", "slack-comp4A", ShipperType.COMPANY_SHIPPER, 1, false),
			CreateDeliveryShipperInfo.from(UUID.randomUUID(),
				UUID.fromString("db73202e-4c64-42ad-b90b-4af70b2079c3"),
				"허브4_업체담당자B", "010-4444-1114", "slack-comp4B", ShipperType.COMPANY_SHIPPER, 2, true)
		);

		return all.stream()
			.filter(shipper -> shipper.hubId().equals(hubId))
			.toList();
	}

	public static List<CreateDeliveryShipperInfo> toCreateDeliveryShipperInfo(
		List<FeignUserResponse> response) {
		return response.stream().map(r -> CreateDeliveryShipperInfo.from(
			r.shipperId(),
			r.hubId(),
			r.shipperName(),
			r.shipperPhoneNumber(),
			r.slackId(),
			r.type(),
			r.order(),
			r.isShippingAvailable()
		)).toList();
	}
}
