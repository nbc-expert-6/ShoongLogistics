package com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.mapper;

import com.shoonglogitics.orderservice.domain.delivery.application.service.dto.CreateDeliveryOrderInfo;
import com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto.FeignOrderResponse;

public final class OrderMapper {
	private OrderMapper() {
	}

	public static CreateDeliveryOrderInfo toCreateDeliveryOrderInfo(FeignOrderResponse response) {
		if (response == null) {
			return null;
		}
		return CreateDeliveryOrderInfo.from(response);
	}
}
