package com.shoonglogitics.orderservice.domain.order.infrastructure.external.dto;

public record FeginStockDecreaseRequest(
	Integer quantity,
	String reason
) {
	public static FeginStockDecreaseRequest from(Integer quantity, String reason) {
		return new FeginStockDecreaseRequest(quantity, reason);
	}
}
