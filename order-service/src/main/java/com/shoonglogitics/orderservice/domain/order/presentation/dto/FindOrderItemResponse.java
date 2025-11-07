package com.shoonglogitics.orderservice.domain.order.presentation.dto;

import java.math.BigDecimal;
import java.util.UUID;

import com.shoonglogitics.orderservice.domain.order.application.dto.FindOrderItemResult;

public record FindOrderItemResponse(
	UUID orderItemId,
	UUID productId,
	BigDecimal price,
	int amount
) {
	public static FindOrderItemResponse from(FindOrderItemResult result) {
		return new FindOrderItemResponse(
			result.orderItemId(),
			result.productId(),
			result.price(),
			result.amount()
		);
	}
}
