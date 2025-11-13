package com.shoonglogitics.companyservice.presentation.productcategory.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateProductCategoryRequest(
	@NotBlank(message = "카테고리 이름은 필수입니다.")
	String name
) {
}
