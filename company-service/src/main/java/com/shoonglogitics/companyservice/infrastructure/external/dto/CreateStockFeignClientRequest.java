package com.shoonglogitics.companyservice.infrastructure.external.dto;

import java.util.UUID;

public record CreateStockFeignClientRequest(
	UUID productId,
	Integer amount
) {
}
