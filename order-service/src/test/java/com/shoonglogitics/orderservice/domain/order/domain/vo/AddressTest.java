package com.shoonglogitics.orderservice.domain.order.domain.vo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AddressTest {

	@Test
	@DisplayName("주소와 위경도로 Address를 생성할 수 있어야 한다")
	void address_withValidFields_ShouldBeCreated() {
		// Given
		GeoLocation location = GeoLocation.of(37.5665, 126.9780);

		// When
		Address address = Address.of("서울특별시", "강남구", "12345", location);

		// Then
		assertThat(address.getAddress()).isEqualTo("서울특별시");
		assertThat(address.getAddressDetail()).isEqualTo("강남구");
		assertThat(address.getZipCode()).isEqualTo("12345");
		assertThat(address.getLocation()).isEqualTo(location);
	}
}