package com.shoonglogitics.companyservice.application.dto.stock;

import java.time.LocalDateTime;
import java.util.UUID;

import com.shoonglogitics.companyservice.domain.stock.entity.Stock;

public record StockResult(
	UUID id,
	UUID productId,
	Integer amount,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {
	public static StockResult from(Stock stock) {
		return new StockResult(
			stock.getId(),
			stock.getProductId(),
			stock.getAmount(),
			stock.getCreatedAt(),
			stock.getUpdatedAt()
		);
	}
}
