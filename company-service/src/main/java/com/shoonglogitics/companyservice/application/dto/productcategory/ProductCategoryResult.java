package com.shoonglogitics.companyservice.application.dto.productcategory;

import java.time.LocalDateTime;
import java.util.UUID;

import com.shoonglogitics.companyservice.domain.productcategory.entity.ProductCategory;

public record ProductCategoryResult(
	UUID id,
	String name,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {
	public static ProductCategoryResult from(ProductCategory productCategory) {
		return new ProductCategoryResult(
			productCategory.getId(),
			productCategory.getName(),
			productCategory.getCreatedAt(),
			productCategory.getUpdatedAt()
		);
	}
}
