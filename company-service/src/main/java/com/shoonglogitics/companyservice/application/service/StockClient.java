package com.shoonglogitics.companyservice.application.service;

import java.util.UUID;

import com.shoonglogitics.companyservice.application.service.dto.StockInfo;

public interface StockClient {
	void createStock(UUID productId, Long userId);
	void deleteStock(UUID productId, Long userId);
	StockInfo getStock(UUID productId, Long userId);
}
