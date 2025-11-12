package com.shoonglogitics.orderservice.domain.order.infrastructure.external.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record FeignProductResponse(
	UUID id,
	BigDecimal price
) {
}
