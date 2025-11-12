package com.shoonglogitics.companyservice.infrastructure.external.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ProductInfoFeignClientResponse(
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
}
