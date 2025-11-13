package com.shoonglogitics.companyservice.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.shoonglogitics.companyservice.domain.productcategory.entity.ProductCategory;
import com.shoonglogitics.companyservice.domain.productcategory.repository.ProductCategoryRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductCategoryRepositoryAdapter implements ProductCategoryRepository {
	private final JpaProductCategoryRepository jpaProductCategoryRepository;

	@Override
	public ProductCategory save(ProductCategory productCategory) {
		return jpaProductCategoryRepository.save(productCategory);
	}

	@Override
	public Optional<ProductCategory> findById(UUID id) {
		return jpaProductCategoryRepository.findById(id);
	}

	@Override
	public Optional<ProductCategory> findByName(String name) {
		return jpaProductCategoryRepository.findByName(name);
	}

	@Override
	public Page<ProductCategory> getProductCategories(String name, Pageable pageable) {
		return jpaProductCategoryRepository.getProductCategories(name, pageable);
	}
}
