package com.shoonglogitics.companyservice.presentation.stock.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.shoonglogitics.companyservice.application.dto.stock.StockResult;

public record FindStockResponse(
	UUID id,
	UUID productId,
	Integer amount,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {
	public static FindStockResponse from(StockResult result) {
		return new FindStockResponse(
			result.id(),
			result.productId(),
			result.amount(),
			result.createdAt(),
			result.updatedAt()
		);
	}
}
