package com.shoonglogitics.companyservice.application.command.productcategory;

import com.shoonglogitics.companyservice.presentation.common.dto.PageRequest;

import lombok.Builder;

@Builder
public record GetProductCategoriesCommand(
	String name,
	PageRequest pageRequest
) {
}
