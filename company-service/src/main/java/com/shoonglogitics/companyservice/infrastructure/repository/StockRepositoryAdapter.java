package com.shoonglogitics.companyservice.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.shoonglogitics.companyservice.domain.stock.repository.StockRepository;
import com.shoonglogitics.companyservice.domain.stock.entity.Stock;
import com.shoonglogitics.companyservice.domain.stock.entity.StockHistory;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StockRepositoryAdapter implements StockRepository {
	private final JpaStockRepository jpaStockRepository;

	@Override
	public Stock save(Stock stock) {
		return jpaStockRepository.save(stock);
	}

	@Override
	public Optional<Stock> findById(UUID id) {
		return jpaStockRepository.findById(id);
	}

	@Override
	public Optional<Stock> findByProductId(UUID productId) {
		return jpaStockRepository.findByProductId(productId);
	}

	@Override
	public Page<Stock> getStocks(UUID productId, Pageable pageable) {
		return jpaStockRepository.getStocks(productId, pageable);
	}

	@Override
	public Page<StockHistory> getStockHistories(UUID stockId, Pageable pageable) {
		return jpaStockRepository.getStockHistories(stockId, pageable);
	}
}
