package com.shoonglogitics.companyservice.domain.productcategory.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shoonglogitics.companyservice.domain.productcategory.entity.ProductCategory;

public interface ProductCategoryRepository {
	ProductCategory save(ProductCategory productCategory);

	Optional<ProductCategory> findById(UUID id);

	Optional<ProductCategory> findByName(String name);

	Page<ProductCategory> getProductCategories(String name, Pageable pageable);
}
