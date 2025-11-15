package com.shoonglogitics.orderservice.domain.product.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.shoonglogitics.orderservice.domain.product.domain.entity.Stock;
import com.shoonglogitics.orderservice.domain.product.domain.repository.StockRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class StockRepositoryAdapter implements StockRepository {
	private final JpaStockRepository jpaStockRepository;

	@Override
	public Optional<Stock> findByProductId(UUID productId) {
		return jpaStockRepository.findByProductId(productId);
	}
}
