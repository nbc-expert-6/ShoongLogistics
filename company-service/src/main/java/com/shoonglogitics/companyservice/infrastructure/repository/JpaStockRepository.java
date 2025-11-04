package com.shoonglogitics.companyservice.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoonglogitics.companyservice.domain.stock.entity.Stock;

@Repository
public interface JpaStockRepository extends JpaRepository<Stock, UUID> {
}
