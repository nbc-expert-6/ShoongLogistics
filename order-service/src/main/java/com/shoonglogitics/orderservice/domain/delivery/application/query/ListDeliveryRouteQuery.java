package com.shoonglogitics.orderservice.domain.delivery.application.query;

import java.util.UUID;

import com.shoonglogitics.orderservice.global.common.dto.PageRequest;

public record ListDeliveryRouteQuery(
	UUID deliveryId,
	PageRequest pageRequest
) {
	public static ListDeliveryRouteQuery from(UUID deliveryId, PageRequest pageRequest) {
		return new ListDeliveryRouteQuery(deliveryId, pageRequest);
	}
}
