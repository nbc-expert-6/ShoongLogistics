package com.shoonglogitics.companyservice.domain.stock.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shoonglogitics.companyservice.domain.stock.entity.Stock;
import com.shoonglogitics.companyservice.domain.stock.entity.StockHistory;

public interface StockRepository {
	Stock save(Stock stock);

	Optional<Stock> findById(UUID id);

	Optional<Stock> findByProductId(UUID productId);

	Page<Stock> getStocks(UUID productId, Pageable pageable);

	Page<StockHistory> getStockHistories(UUID stockId, Pageable pageable);
}
