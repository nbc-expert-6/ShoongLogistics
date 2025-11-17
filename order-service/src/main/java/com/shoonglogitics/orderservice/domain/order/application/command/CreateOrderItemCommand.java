package com.shoonglogitics.orderservice.domain.order.application.command;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Builder;

@Builder
public record CreateOrderItemCommand(
	UUID productId,
	BigDecimal price,
	Integer quantity
) {
}
