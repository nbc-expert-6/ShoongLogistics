package com.shoonglogitics.companyservice.presentation.company.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateProductRequest(
	@NotNull
	UUID productCategoryId,
	@NotBlank
	String name,
	@NotNull
	Integer price,
	String description
) {
}
