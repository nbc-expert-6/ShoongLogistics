package com.shoonglogitics.orderservice.domain.delivery.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class DeliveryEstimate {
	@Column(name = "estimated_distance")
	private Long distance;
	@Column(name = "estimated_duration")
	private Integer duration;

	public static DeliveryEstimate of(Long distance, Integer duration) {
		if (distance < 0) {
			throw new IllegalArgumentException("거리는 0 이상이어야 합니다. 단위: 미터");
		}
		if (duration < 0) {
			throw new IllegalArgumentException("소요시간은 0 이상이어야 합니다. 단위: 분");
		}

		DeliveryEstimate estimate = new DeliveryEstimate();
		estimate.distance = distance;
		estimate.duration = duration;
		return estimate;
	}
}
