package com.shoonglogitics.companyservice.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shoonglogitics.companyservice.domain.stock.entity.Stock;
import com.shoonglogitics.companyservice.domain.stock.entity.StockHistory;

@Repository
public interface JpaStockRepository extends JpaRepository<Stock, UUID> {
	Optional<Stock> findByProductId(UUID productId);

	@Query("SELECT s FROM Stock s WHERE " +
		"(:productId IS NULL OR s.productId = :productId)")
	Page<Stock> getStocks(
		@Param("productId") UUID productId,
		Pageable pageable
	);

	@Query("SELECT sh FROM Stock s " +
		"JOIN s.stockHistories sh " +
		"WHERE s.id = :stockId " +
		"ORDER BY sh.createdAt DESC")
	Page<StockHistory> getStockHistories(
		@Param("stockId") UUID stockId,
		Pageable pageable
	);
}
