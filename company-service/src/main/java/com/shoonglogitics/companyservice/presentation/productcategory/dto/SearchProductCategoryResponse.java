package com.shoonglogitics.companyservice.presentation.productcategory.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.shoonglogitics.companyservice.application.dto.productcategory.ProductCategoryResult;

public record SearchProductCategoryResponse(
	UUID id,
	String name,
	LocalDateTime createdAt
) {
	public static SearchProductCategoryResponse from(ProductCategoryResult result) {
		return new SearchProductCategoryResponse(
			result.id(),
			result.name(),
			result.createdAt()
		);
	}
}
