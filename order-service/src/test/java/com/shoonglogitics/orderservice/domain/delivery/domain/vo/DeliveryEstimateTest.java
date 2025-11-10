package com.shoonglogitics.orderservice.domain.delivery.domain.vo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DeliveryEstimateTest {

	@Test
	@DisplayName("DeliveryEstimate 객체 생성 시 거리와 소요시간이 정상적으로 세팅되어야 한다")
	void createDeliveryEstimate_ShouldSetAllFields() {
		// Given
		Long distance = 2000L;
		Integer duration = 30;

		// When
		DeliveryEstimate estimate = DeliveryEstimate.of(distance, duration);

		// Then
		assertThat(estimate).isNotNull();
		assertThat(estimate.getDistance()).isEqualTo(distance);
		assertThat(estimate.getDuration()).isEqualTo(duration);
	}

	@Test
	@DisplayName("거리(distance)가 음수이면 예외가 발생해야 한다")
	void createDeliveryEstimate_WithNegativeDistance_ShouldThrowException() {
		// Given
		Long distance = -100L;
		Integer duration = 30;

		// When / Then
		assertThatThrownBy(() -> DeliveryEstimate.of(distance, duration))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("거리는 0 이상이어야 합니다");
	}

	@Test
	@DisplayName("소요시간(duration)이 음수이면 예외가 발생해야 한다")
	void createDeliveryEstimate_WithNegativeDuration_ShouldThrowException() {
		// Given
		Long distance = 2000L;
		Integer duration = -5;

		// When / Then
		assertThatThrownBy(() -> DeliveryEstimate.of(distance, duration))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("소요시간은 0 이상이어야 합니다");
	}
}