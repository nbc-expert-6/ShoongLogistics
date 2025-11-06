package com.shoonglogitics.orderservice.domain.order.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Quentity {
	private int value;

	private Quentity(int value) {
		this.value = value;
	}

	public static Quentity of(int value) {
		if (value < 1) {
			throw new IllegalArgumentException("주문 수량은 최소 1개 이상이어야 합니다.");
		}
		return new Quentity(value);
	}

}
