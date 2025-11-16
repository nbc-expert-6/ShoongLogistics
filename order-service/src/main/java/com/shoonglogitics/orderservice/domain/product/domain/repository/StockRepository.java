package com.shoonglogitics.orderservice.domain.product.domain.repository;

import java.util.Optional;
import java.util.UUID;

import com.shoonglogitics.orderservice.domain.product.domain.entity.Stock;

public interface StockRepository {
	Optional<Stock> findByProductId(UUID productId);

	Stock save(Stock stock);
}
