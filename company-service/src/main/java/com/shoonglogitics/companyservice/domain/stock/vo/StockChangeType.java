package com.shoonglogitics.companyservice.domain.stock.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StockChangeType {
	ORDER("주문", "주문으로 인한 재고 감소"),
	ORDER_CANCEL("주문 취소", "주문 취소로 인한 재고 증가"),
	STOCK_IN("입고", "입고로 인한 재고 증가"),
	STOCK_OUT("출고", "출고로 인한 재고 감소");

	private final String description;
	private final String detail;
}
