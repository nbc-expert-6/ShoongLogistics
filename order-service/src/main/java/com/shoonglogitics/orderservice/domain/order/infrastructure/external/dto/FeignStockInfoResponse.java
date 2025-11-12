package com.shoonglogitics.orderservice.domain.order.infrastructure.external.dto;

import java.util.UUID;

public record FeignStockInfoResponse(
	UUID id,
	UUID productId,
	Integer amount
) {
}
