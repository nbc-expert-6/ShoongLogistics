package com.shoonglogitics.companyservice.application.command.stock;

import java.util.UUID;

import com.shoonglogitics.companyservice.domain.common.vo.AuthUser;

import lombok.Builder;

@Builder
public record CreateStockCommand(
	AuthUser authUser,
	UUID productId,
	Integer initialAmount
) {
}
