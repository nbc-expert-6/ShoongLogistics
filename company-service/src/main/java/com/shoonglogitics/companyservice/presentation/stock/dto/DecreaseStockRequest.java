package com.shoonglogitics.companyservice.presentation.stock.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DecreaseStockRequest(
	@NotNull(message = "감소 수량은 필수입니다.")
	@Min(value = 1, message = "감소 수량은 1 이상이어야 합니다.")
	Integer quantity,
	
	@NotBlank(message = "감소 사유는 필수입니다.")
	String reason
) {
}
