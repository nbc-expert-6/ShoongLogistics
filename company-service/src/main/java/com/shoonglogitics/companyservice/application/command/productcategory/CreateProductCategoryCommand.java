package com.shoonglogitics.companyservice.application.command.productcategory;

import com.shoonglogitics.companyservice.domain.common.vo.AuthUser;

import lombok.Builder;

@Builder
public record CreateProductCategoryCommand(
	AuthUser authUser,
	String name
) {
}
