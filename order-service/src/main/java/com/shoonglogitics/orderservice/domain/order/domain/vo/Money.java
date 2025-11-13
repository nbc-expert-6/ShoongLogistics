package com.shoonglogitics.orderservice.domain.order.domain.vo;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@EqualsAndHashCode
public class Money {

	@Column(name = "total_price", nullable = false)
	private BigDecimal amount;

	private Money(BigDecimal amount) {
		if (amount.compareTo(BigDecimal.ZERO) < 0) {
			throw new IllegalArgumentException("결제금액은 0보다 작을 수 없습니다.");
		}
		if (amount.scale() > 2) {
			throw new IllegalArgumentException("금액은 소수점 둘째 자리까지만 가능합니다");
		}
		this.amount = amount;
	}

	public static Money of(BigDecimal amount) {
		return new Money(amount);
	}

	public boolean isNegative() {
		return this.amount.compareTo(BigDecimal.ZERO) < 0;
	}

	public static Money zero() {
		return new Money(BigDecimal.ZERO);
	}

	public Money add(Money other) {
		return new Money(this.amount.add(other.amount));
	}

	public Money multiply(int quantity) {
		return new Money(this.amount.multiply(BigDecimal.valueOf(quantity)));
	}
}
