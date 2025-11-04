package com.shoonglogitics.companyservice.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shoonglogitics.companyservice.domain.productcategory.entity.ProductCategory;

@Repository
public interface JpaProductCategoryRepository extends JpaRepository<ProductCategory, UUID> {
}
