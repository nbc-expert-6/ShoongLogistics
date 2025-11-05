package com.shoonglogitics.orderservice.domain.order.application.command;

import java.util.UUID;

public record CreateOrderItemCommand(
	UUID productId,
	Integer amount
) {
}
