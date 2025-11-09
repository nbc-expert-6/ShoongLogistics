package com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.mapper;

import com.shoonglogitics.orderservice.domain.delivery.application.service.dto.OrderInfo;
import com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto.FeignOrderResponse;

public final class OrderMapper {
	private OrderMapper() {
	}

	public static OrderInfo toCreateDeliveryOrderInfo(FeignOrderResponse response) {
		if (response == null) {
			return null;
		}
		return OrderInfo.from(response);
	}
}
