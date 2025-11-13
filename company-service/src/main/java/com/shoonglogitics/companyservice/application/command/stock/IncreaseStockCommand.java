package com.shoonglogitics.companyservice.application.command.stock;

import java.util.UUID;

import com.shoonglogitics.companyservice.domain.common.vo.AuthUser;

public record IncreaseStockCommand(
	AuthUser authUser,
	UUID stockId,
	Integer quantity,
	String reason
) {
}
