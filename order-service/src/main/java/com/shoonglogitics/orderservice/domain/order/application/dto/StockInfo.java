package com.shoonglogitics.orderservice.domain.order.application.dto;

import java.util.UUID;

public record StockInfo(
	UUID stockId,
	UUID productId,
	Integer amount
) {
	public static StockInfo from(UUID stockId, UUID productId, Integer amount) {
		return new StockInfo(stockId, productId, amount);
	}
}
