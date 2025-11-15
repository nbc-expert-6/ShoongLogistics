package com.shoonglogitics.orderservice.domain.product.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoonglogitics.orderservice.domain.product.domain.entity.Stock;

public interface JpaStockRepository extends JpaRepository<Stock, UUID> {
	Optional<Stock> findByProductId(UUID productId);
}
