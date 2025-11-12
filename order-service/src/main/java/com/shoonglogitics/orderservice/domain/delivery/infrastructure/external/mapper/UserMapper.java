package com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.mapper;

import java.util.List;

import com.shoonglogitics.orderservice.domain.delivery.application.service.dto.ShipperInfo;
import com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto.FeignUserResponse;

public final class UserMapper {

	public static List<ShipperInfo> toCreateDeliveryShipperInfo(
		List<FeignUserResponse> response) {
		return response.stream().map(r -> ShipperInfo.from(
			r.userId(),
			r.hubId(),
			r.name(),
			r.phoneNumber(),
			r.slackId(),
			r.shipperType(),
			r.order(),
			r.isShippingAvailable()
		)).toList();
	}
}
