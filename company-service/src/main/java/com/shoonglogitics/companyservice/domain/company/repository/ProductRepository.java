package com.shoonglogitics.companyservice.domain.company.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shoonglogitics.companyservice.domain.company.entity.Product;

public interface ProductRepository {
	public Product createProduct(Product product);
	public Page<Product> searchProducts(UUID companyId, List<UUID> productCategoryId, Pageable pageable);
}
