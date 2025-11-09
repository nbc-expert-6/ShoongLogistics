package com.shoonglogitics.orderservice.domain.order.infrastructure.external.mapper;

import com.shoonglogitics.orderservice.domain.order.application.service.dto.DeliveryInfo;
import com.shoonglogitics.orderservice.domain.order.infrastructure.external.dto.FeignDeliveryResponse;

public final class DeliveryMapper {

	public static DeliveryInfo toDeliveryInfo(FeignDeliveryResponse response) {
		return DeliveryInfo.from(response.deliveryId());
	}
}
