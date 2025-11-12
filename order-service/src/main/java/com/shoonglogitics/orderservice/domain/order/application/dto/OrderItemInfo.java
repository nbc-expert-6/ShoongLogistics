package com.shoonglogitics.orderservice.domain.order.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemInfo(
	UUID productId,
	BigDecimal price
) {
	public static OrderItemInfo from(
		UUID productId,
		BigDecimal price
	) {
		return new OrderItemInfo(productId, price);
	}
}
