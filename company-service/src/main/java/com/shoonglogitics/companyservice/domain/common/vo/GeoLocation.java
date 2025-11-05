package com.shoonglogitics.companyservice.domain.common.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class GeoLocation {
	private Double latitude;
	private Double longitude;

	private GeoLocation(Double latitude, Double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public static GeoLocation of(Double latitude, Double longitude) {
		validateCoordinates(latitude, longitude);
		return new GeoLocation(latitude, longitude);
	}

	private static void validateCoordinates(Double latitude, Double longitude) {
		if (latitude < -90 || latitude > 90) {
			throw new IllegalArgumentException("위도는 -90 ~ 90 사이여야 합니다.");
		}

		if (longitude < -180 || longitude > 180) {
			throw new IllegalArgumentException("경도는 -180 ~ 180 사이여야 합니다.");
		}
	}
}
