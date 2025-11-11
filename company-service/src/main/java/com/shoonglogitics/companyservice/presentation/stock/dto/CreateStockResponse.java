package com.shoonglogitics.companyservice.presentation.stock.dto;

import java.util.UUID;

public record CreateStockResponse(
	UUID stockId,
	String message
) {
}
