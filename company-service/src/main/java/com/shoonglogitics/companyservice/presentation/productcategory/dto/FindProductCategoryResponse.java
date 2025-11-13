package com.shoonglogitics.companyservice.presentation.productcategory.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.shoonglogitics.companyservice.application.dto.productcategory.ProductCategoryResult;

public record FindProductCategoryResponse(
	UUID id,
	String name,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {
	public static FindProductCategoryResponse from(ProductCategoryResult result) {
		return new FindProductCategoryResponse(
			result.id(),
			result.name(),
			result.createdAt(),
			result.updatedAt()
		);
	}
}
