package com.shoonglogitics.hubservice.domain.vo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class GeoLocation {

	private Double latitude;
	private Double longitude;

	private GeoLocation(Double latitude, Double longitude) {
		validate(latitude, longitude);
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public static GeoLocation of(Double latitude, Double longitude) {
		validate(latitude, longitude);
		return new GeoLocation(latitude, longitude);
	}

	public static void validate(Double latitude, Double longitude) {
		if (latitude == null || longitude == null) {
			throw new IllegalArgumentException("위도와 경도는 필수입니다");
		}
		if (latitude < -90 || latitude > 90) {
			throw new IllegalArgumentException("위도는 -90~90 사이여야 합니다");
		}
		if (longitude < -180 || longitude > 180) {
			throw new IllegalArgumentException("경도는 -180~180 사이여야 합니다");
		}
	}

}
