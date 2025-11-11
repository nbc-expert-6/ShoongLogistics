package com.shoonglogitics.companyservice.application.dto.company;

import java.time.LocalDateTime;
import java.util.UUID;

import com.shoonglogitics.companyservice.domain.company.entity.Product;

public record ProductResult(
	UUID id,
	UUID productCategoryId,
	String name,
	Integer price,
	String description,
	LocalDateTime createdAt,
	Long createdBy,
	LocalDateTime updatedAt,
	Long updatedBy
) {
	public static ProductResult from(Product product) {
		return new ProductResult(
			product.getId(),
			product.getProductCategoryId(),
			product.getName(),
			product.getPrice(),
			product.getDescription(),
			product.getCreatedAt(),
			product.getCreatedBy(),
			product.getUpdatedAt(),
			product.getUpdatedBy()
		);
	}
}
