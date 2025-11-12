package com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.mapper;

import java.util.List;
import java.util.UUID;

import com.shoonglogitics.orderservice.domain.delivery.application.service.dto.RoutesInfo;
import com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto.FeignHubResponse;

public final class HubMapper {
	public HubMapper() {
	}

	//실제 로직
	public static List<RoutesInfo> toCreateDeliveryRoutesInfo(List<FeignHubResponse> data) {

		return data.stream()
			.map(r -> new RoutesInfo(
				r.departureHubId(),
				r.arrivalHubId(),
				r.sequence(),
				r.distance(),
				r.duration()
			))
			.toList();
	}

	//더미 응답
	public static List<RoutesInfo> toCreateDeliveryRoutesInfoDummy() {
		return List.of(
			RoutesInfo.from(
				UUID.fromString("8a54a9c4-7e9d-4d9b-b25f-1f4b8f3e6a77"), // 출발(공급자 허브)
				UUID.fromString("b19f84b1-3c56-4c9c-8f85-1b3d93e1e4b2"), // 1차 허브
				1,
				18000L,
				120
			),
			RoutesInfo.from(
				UUID.fromString("b19f84b1-3c56-4c9c-8f85-1b3d93e1e4b2"), // 1차 허브
				UUID.fromString("a72cfa9f-12b1-4b94-8e31-3c8bfe2df51d"), // 2차 허브
				2,
				6000L,
				70
			),
			RoutesInfo.from(
				UUID.fromString("a72cfa9f-12b1-4b94-8e31-3c8bfe2df51d"), // 2차 허브
				UUID.fromString("db73202e-4c64-42ad-b90b-4af70b2079c3"), // 최종(수령자 허브)
				3,
				5500L,
				60
			)
		);
	}
}
