package com.shoonglogitics.companyservice.domain.common.vo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * 값 객체 테스트 - GeoLocation
 */
class GeoLocationTest {

	@Test
	@DisplayName("유효한 위경도로 GeoLocation을 생성할 수 있다")
	void createValidGeoLocation() {
		// Given & When
		GeoLocation location = GeoLocation.of(37.5665, 126.9780);

		// Then
		assertThat(location.getLatitude()).isEqualTo(37.5665);
		assertThat(location.getLongitude()).isEqualTo(126.9780);
	}

	@ParameterizedTest
	@DisplayName("위도 범위를 벗어나면 생성할 수 없다")
	@ValueSource(doubles = {-90.1, -91.0, -100.0, 90.1, 91.0, 100.0})
	void invalidLatitude(double invalidLatitude) {
		// When & Then
		assertThatThrownBy(() -> GeoLocation.of(invalidLatitude, 126.9780))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("위도는 -90 ~ 90 사이여야 합니다");
	}

	@ParameterizedTest
	@DisplayName("경도 범위를 벗어나면 생성할 수 없다")
	@ValueSource(doubles = {-180.1, -181.0, -200.0, 180.1, 181.0, 200.0})
	void invalidLongitude(double invalidLongitude) {
		// When & Then
		assertThatThrownBy(() -> GeoLocation.of(37.5665, invalidLongitude))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("경도는 -180 ~ 180 사이여야 합니다");
	}

	@ParameterizedTest
	@DisplayName("유효한 위경도 범위 - 경계값 포함")
	@CsvSource({
		"-90.0, -180.0",   // 최소값
		"-90.0, 0.0",
		"-90.0, 180.0",
		"0.0, -180.0",
		"0.0, 0.0",        // 중앙값
		"0.0, 180.0",
		"90.0, -180.0",
		"90.0, 0.0",
		"90.0, 180.0"     // 최대값
	})
	void validCoordinates(double latitude, double longitude) {
		// When
		GeoLocation location = GeoLocation.of(latitude, longitude);

		// Then
		assertThat(location.getLatitude()).isEqualTo(latitude);
		assertThat(location.getLongitude()).isEqualTo(longitude);
	}

	@Test
	@DisplayName("같은 위경도의 GeoLocation 객체는 동일하다")
	void equality() {
		// Given
		GeoLocation location1 = GeoLocation.of(37.5665, 126.9780);
		GeoLocation location2 = GeoLocation.of(37.5665, 126.9780);

		// Then
		assertThat(location1).isEqualTo(location2);
		assertThat(location1.hashCode()).isEqualTo(location2.hashCode());
	}

	@ParameterizedTest
	@DisplayName("다른 위경도의 GeoLocation 객체는 동일하지 않다")
	@CsvSource({
		"37.5665, 126.9780, 37.5665, 126.9781",  // 경도만 다름
		"37.5665, 126.9780, 37.5666, 126.9780",  // 위도만 다름
		"37.5665, 126.9780, 35.1796, 129.0756"   // 둘 다 다름 (서울 vs 부산)
	})
	void inequality(double lat1, double lon1, double lat2, double lon2) {
		// Given
		GeoLocation location1 = GeoLocation.of(lat1, lon1);
		GeoLocation location2 = GeoLocation.of(lat2, lon2);

		// Then
		assertThat(location1).isNotEqualTo(location2);
	}
}
