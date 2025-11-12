package com.shoonglogitics.companyservice.application.command;

import java.util.UUID;

import com.shoonglogitics.companyservice.domain.common.vo.AuthUser;

import lombok.Builder;

@Builder
public record UpdateProductCommand(
	AuthUser authUser,
	UUID companyId,
	UUID productId,
	UUID productCategoryId,
	String name,
	Integer price,
	String description
) {
}

