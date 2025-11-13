package com.shoonglogitics.orderservice.domain.order.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.shoonglogitics.orderservice.domain.order.domain.entity.OrderItem;

public record FindOrderItemResult(
	UUID orderItemId,
	UUID productId,
	BigDecimal price,
	int amount
) {
	public static FindOrderItemResult from(OrderItem orderItem) {
		return new FindOrderItemResult(
			orderItem.getId(),
			orderItem.getProductInfo().getProductId(),
			orderItem.getProductInfo().getPrice().getAmount(),
			orderItem.getQuantity().getValue()
		);
	}
}
