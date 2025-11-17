package com.shoonglogitics.orderservice.domain.payment.domain.vo;

import lombok.Getter;

@Getter
public enum PaymentStatus {
	// PENDING, COMPLETED, FAILED
	PENDING("결제 대기"),
	COMPLETED("결제 완료"),
	FAILED("결제 실패");

	private final String description;

	PaymentStatus(String description) {
		this.description = description;
	}
}
