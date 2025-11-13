package com.shoonglogitics.companyservice.application.command;

import java.util.UUID;

import com.shoonglogitics.companyservice.domain.common.vo.AuthUser;

public record DeleteProductCommand(
	AuthUser authUser,
	UUID companyId,
	UUID productId
) {
}
