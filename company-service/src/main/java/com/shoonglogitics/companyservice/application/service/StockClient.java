package com.shoonglogitics.companyservice.application.service;

import java.util.UUID;

public interface StockClient {
	void createStock(UUID productId, Long userId);
	void deleteStock(UUID productId, Long userId);
}
