package com.shoonglogitics.companyservice.presentation.company.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.shoonglogitics.companyservice.application.dto.company.ProductResult;

public record SearchProductResponse(
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

	public static SearchProductResponse from(ProductResult result) {
		return new SearchProductResponse(
			result.id(),
			result.productCategoryId(),
			result.name(),
			result.price(),
			result.description(),
			result.createdAt(),
			result.createdBy(),
			result.updatedAt(),
			result.updatedBy()
		);
	}
}

