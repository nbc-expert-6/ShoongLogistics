package com.shoonglogitics.companyservice.infrastructure.repository;

import org.springframework.stereotype.Component;

import com.shoonglogitics.companyservice.domain.company.repository.ProductCategoryRepository;
import com.shoonglogitics.companyservice.domain.productcategory.entity.ProductCategory;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductCategoryRepositoryAdapter implements ProductCategoryRepository {
	private final JpaProductCategoryRepository jpaProductCategoryRepository;

	@Override
	public ProductCategory save(ProductCategory productCategory) {
		return jpaProductCategoryRepository.save(productCategory);
	}
}
