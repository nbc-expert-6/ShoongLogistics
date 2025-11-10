package com.shoonglogitics.orderservice.domain.delivery.domain.vo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DeliveryActualTest {

	@Test
	@DisplayName("DeliveryActual 객체 생성 시 거리와 소요시간이 정상적으로 세팅되어야 한다")
	void createDeliveryActual_ShouldSetAllFields() {
		// Given
		Long distance = 1000L;
		Integer duration = 15;

		// When
		DeliveryActual actual = DeliveryActual.of(distance, duration);

		// Then
		assertThat(actual).isNotNull();
		assertThat(actual.getDistance()).isEqualTo(distance);
		assertThat(actual.getDuration()).isEqualTo(duration);
	}

	@Test
	@DisplayName("거리가 음수이면 예외가 발생해야 한다")
	void createDeliveryActual_WithNegativeDistance_ShouldThrowException() {
		// Given
		Long distance = -100L;
		Integer duration = 15;

		// When / Then
		assertThatThrownBy(() -> DeliveryActual.of(distance, duration))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("거리는 0 이상이어야 합니다");
	}

	@Test
	@DisplayName("소요시간이 음수이면 예외가 발생해야 한다")
	void createDeliveryActual_WithNegativeDuration_ShouldThrowException() {
		// Given
		Long distance = 1000L;
		Integer duration = -5;

		// When / Then
		assertThatThrownBy(() -> DeliveryActual.of(distance, duration))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("소요시간은 0 이상이어야 합니다");
	}
}