package com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.mapper;

import java.util.List;

import com.shoonglogitics.orderservice.domain.delivery.application.service.dto.ShipperInfo;
import com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto.FeignUserResponse;

public final class UserMapper {

	public static List<ShipperInfo> toCreateDeliveryShipperInfo(
		List<FeignUserResponse> response) {
		return response.stream().map(r -> ShipperInfo.from(
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
