package com.shoonglogitics.companyservice.presentation.stock.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.shoonglogitics.companyservice.application.dto.stock.StockResult;

public record SearchStockResponse(
	UUID id,
	UUID productId,
	Integer amount,
	LocalDateTime createdAt
) {
	public static SearchStockResponse from(StockResult result) {
		return new SearchStockResponse(
			result.id(),
			result.productId(),
			result.amount(),
			result.createdAt()
		);
	}
}
