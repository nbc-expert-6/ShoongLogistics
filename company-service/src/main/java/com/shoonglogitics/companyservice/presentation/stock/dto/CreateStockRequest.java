package com.shoonglogitics.companyservice.presentation.stock.dto;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateStockRequest(
	@NotNull(message = "상품 ID는 필수입니다.")
	UUID productId,
	
	@NotNull(message = "초기 재고 수량은 필수입니다.")
	@Min(value = 0, message = "초기 재고 수량은 0 이상이어야 합니다.")
	Integer initialAmount
) {
}
