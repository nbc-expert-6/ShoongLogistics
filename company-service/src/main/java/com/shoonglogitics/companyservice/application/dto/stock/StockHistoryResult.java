package com.shoonglogitics.companyservice.application.dto.stock;

import java.time.LocalDateTime;
import java.util.UUID;

import com.shoonglogitics.companyservice.domain.stock.entity.StockHistory;
import com.shoonglogitics.companyservice.domain.stock.vo.StockChangeType;

public record StockHistoryResult(
	UUID id,
	UUID orderId,
	Integer beforeAmount,
	Integer changeAmount,
	Integer afterAmount,
	StockChangeType changeType,
	String reason,
	LocalDateTime createdAt
) {
	public static StockHistoryResult from(StockHistory stockHistory) {
		return new StockHistoryResult(
			stockHistory.getId(),
			stockHistory.getOrderId(),
			stockHistory.getStockChange().getBeforeAmount(),
			stockHistory.getStockChange().getChangeAmount(),
			stockHistory.getStockChange().getAfterAmount(),
			stockHistory.getChangeType(),
			stockHistory.getReason(),
			stockHistory.getCreatedAt()
		);
	}
}
