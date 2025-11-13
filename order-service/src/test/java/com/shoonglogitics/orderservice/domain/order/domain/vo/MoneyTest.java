package com.shoonglogitics.orderservice.domain.order.domain.vo;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MoneyTest {

	@Test
	@DisplayName("0 이상의 금액으로 Money를 생성할 수 있어야 한다")
	void money_withValidAmount_ShouldBeCreated() {
		// Given & When
		Money money = Money.of(new BigDecimal("100.00"));

		// Then
		assertThat(money.getAmount()).isEqualTo(new BigDecimal("100.00"));
		assertThat(money.isNegative()).isFalse();
	}

	@ParameterizedTest
	@DisplayName("음수 금액은 Money 생성 시 예외가 발생해야 한다")
	@ValueSource(doubles = {-0.01, -1.0, -100.5})
	void money_withNegativeAmount_ShouldThrowException(double amount) {
		// Given & When & Then
		assertThatThrownBy(() -> Money.of(BigDecimal.valueOf(amount)))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("결제금액은 0보다 작을 수 없습니다.");
	}

	@Test
	@DisplayName("Money를 더하면 새로운 Money 객체가 생성되어야 한다")
	void money_addMoney_ShouldReturnNewMoney() {
		// Given
		Money m1 = Money.of(new BigDecimal("100.00"));
		Money m2 = Money.of(new BigDecimal("50.50"));

		// When
		Money result = m1.add(m2);

		// Then
		assertThat(result.getAmount()).isEqualTo(new BigDecimal("150.50"));
	}

	@Test
	@DisplayName("Money를 곱하면 새로운 Money 객체가 생성되어야 한다")
	void money_multiplyMoney_ShouldReturnNewMoney() {
		// Given
		Money m1 = Money.of(new BigDecimal("100.00"));

		// When
		Money result = m1.multiply(3);

		// Then
		assertThat(result.getAmount()).isEqualTo(new BigDecimal("300.00"));
	}

	@Test
	@DisplayName("Money.zero()는 0 금액을 가진 객체를 반환해야 한다")
	void money_zero_ShouldReturnZeroMoney() {
		// When
		Money zero = Money.zero();

		// Then
		assertThat(zero.getAmount()).isEqualTo(BigDecimal.ZERO);
	}
}