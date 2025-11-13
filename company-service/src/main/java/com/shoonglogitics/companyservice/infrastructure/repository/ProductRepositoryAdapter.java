package com.shoonglogitics.companyservice.infrastructure.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.shoonglogitics.companyservice.domain.company.entity.Product;
import com.shoonglogitics.companyservice.domain.company.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {
	private final JpaProductRepository jpaProductRepository;

	@Override
	public Product createProduct(Product product) {
		return jpaProductRepository.save(product);
	}

	@Override
	public Page<Product> searchProducts(UUID companyId, List<UUID> productCategoryId, Pageable pageable) {
		return null;
	}
}
