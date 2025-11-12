package com.shoonglogitics.companyservice.presentation.productcategory.dto;

import java.util.UUID;

public record UpdateProductCategoryResponse(
	UUID productCategoryId,
	String message
) {
}
