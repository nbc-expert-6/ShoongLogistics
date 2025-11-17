package com.shoonglogitics.orderservice.domain.product.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Quantity {
	private Integer quantity;

	public static Quantity of(Integer quantity) {
		if (quantity == null) {
			throw new IllegalArgumentException("재고 수량은 null일 수 없습니다.");
		}
		if (quantity < 0) {
			throw new IllegalArgumentException("재고 수량은 음수일 수 없습니다.");
		}
		Quantity createdQuantity = new Quantity();
		createdQuantity.quantity = quantity;
		return createdQuantity;
	}

	public Quantity add(int amount) {
		return Quantity.of(this.quantity + amount);
	}

	public Quantity subtract(int amount) {
		return Quantity.of(this.quantity - amount);
	}
}
