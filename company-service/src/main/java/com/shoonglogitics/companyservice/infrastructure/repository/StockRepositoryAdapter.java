package com.shoonglogitics.companyservice.infrastructure.repository;

import org.springframework.stereotype.Component;

import com.shoonglogitics.companyservice.domain.company.repository.StockRepository;
import com.shoonglogitics.companyservice.domain.stock.entity.Stock;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StockRepositoryAdapter implements StockRepository {
	private final JpaStockRepository jpaStockRepository;

	@Override
	public Stock save(Stock stock) {
		return jpaStockRepository.save(stock);
	}
}
