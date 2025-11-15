package com.shoonglogitics.orderservice.domain.order.application.command;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateOrderItemCommand(
	UUID productId,
	BigDecimal price,
	Integer quantity
) {
}
