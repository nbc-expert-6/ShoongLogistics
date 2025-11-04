package com.shoonglogitics.companyservice.domain.company.repository;

import com.shoonglogitics.companyservice.domain.stock.entity.Stock;

public interface StockRepository {
	Stock save(Stock company);
}
