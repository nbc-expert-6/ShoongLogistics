package com.shoonglogitics.orderservice.domain.order.infrastructure.external.dto;

import java.util.UUID;

public record FeignDeliveryResponse(
	UUID deliveryId
) {
}
