package com.shoonglogitics.companyservice.application.command.productcategory;

import java.util.UUID;

import com.shoonglogitics.companyservice.domain.common.vo.AuthUser;

public record DeleteProductCategoryCommand(
	AuthUser authUser,
	UUID productCategoryId
) {
}
