package com.shoonglogitics.companyservice.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoonglogitics.companyservice.domain.company.entity.Product;

public interface JpaProductRepository extends JpaRepository<Product, UUID> {

}
