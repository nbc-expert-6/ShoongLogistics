package com.shoonglogitics.companyservice.infrastructure.external.dto;

import java.util.UUID;

public record ProductCategoryInfoFeignClientResponse(
	UUID productCategoryId,
	String name
) {
}
