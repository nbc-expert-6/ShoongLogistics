package com.shoonglogitics.companyservice.domain.stock.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StockChange {

	private Integer beforeAmount;
	private Integer changeAmount;
	private Integer afterAmount;

	private StockChange(Integer beforeAmount, Integer changeAmount, Integer afterAmount) {
		validateStockChange(beforeAmount, changeAmount, afterAmount);

		this.beforeAmount = beforeAmount;
		this.changeAmount = changeAmount;
		this.afterAmount = afterAmount;
	}

	public static StockChange of(Integer beforeAmount, Integer changeAmount, Integer afterAmount) {
		return new StockChange(beforeAmount, changeAmount, afterAmount);
	}

	private void validateStockChange(Integer beforeAmount, Integer changeAmount, Integer afterAmount) {
		if (beforeAmount < 0 || afterAmount < 0) {
			throw new IllegalArgumentException("재고 수량은 음수일 수 없습니다.");
		}

		// 변경 전 + 변경량 = 변경 후 검증
		if (beforeAmount + changeAmount != afterAmount) {
			throw new IllegalArgumentException(
				String.format("재고 변경 계산이 올바르지 않습니다. before: %d, change: %d, after: %d",
					beforeAmount, changeAmount, afterAmount)
			);
		}
	}
}
