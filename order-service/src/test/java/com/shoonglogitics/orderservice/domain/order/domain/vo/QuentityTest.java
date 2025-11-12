package com.shoonglogitics.orderservice.domain.order.domain.vo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class QuentityTest {

	@Test
	@DisplayName("유효한 수량으로 Quentity 객체를 생성할 수 있어야 한다")
	void quentity_withValidValue_ShouldBeCreated() {
		// Given
		int value = 5;

		// When
		Quantity quantity = Quantity.of(value);

		// Then
		assertThat(quantity.getValue()).isEqualTo(value);
	}

	@ParameterizedTest
	@ValueSource(ints = {0, -1, -100})
	@DisplayName("1 미만의 수량으로 Quentity 객체를 생성하면 예외가 발생해야 한다")
	void quentity_withInvalidValue_ShouldThrowException(int invalidValue) {
		// Given & When & Then
		assertThatThrownBy(() -> Quantity.of(invalidValue))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("주문 수량은 최소 1개 이상이어야 합니다.");
	}
}