package com.shoonglogitics.companyservice.presentation.productcategory.dto;

import java.util.UUID;

public record CreateProductCategoryResponse(
	UUID productCategoryId,
	String message
) {
}
