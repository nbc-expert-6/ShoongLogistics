package com.shoonglogitics.companyservice.presentation.stock.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.shoonglogitics.companyservice.application.dto.stock.StockHistoryResult;
import com.shoonglogitics.companyservice.domain.stock.vo.StockChangeType;

public record StockHistoryResponse(
	UUID id,
	UUID orderId,
	Integer beforeAmount,
	Integer changeAmount,
	Integer afterAmount,
	StockChangeType changeType,
	String changeTypeDescription,
	String reason,
	LocalDateTime createdAt
) {
	public static StockHistoryResponse from(StockHistoryResult result) {
		return new StockHistoryResponse(
			result.id(),
			result.orderId(),
			result.beforeAmount(),
			result.changeAmount(),
			result.afterAmount(),
			result.changeType(),
			result.changeType().getDescription(),
			result.reason(),
			result.createdAt()
		);
	}
}
