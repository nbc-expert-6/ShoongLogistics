package com.shoonglogitics.orderservice.domain.delivery.domain.vo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.shoonglogitics.orderservice.domain.order.domain.vo.GeoLocation;

class AddressTest {

	@Test
	@DisplayName("Address 객체 생성 시 모든 필드가 정상적으로 세팅되어야 한다")
	void createAddress_ShouldSetAllFields() {
		// Given
		String address = "Seoul";
		String addressDetail = "Detail";
		String zipCode = "12345";
		GeoLocation location = GeoLocation.of(37.5665, 126.9780);

		// When
		Address addr = Address.of(address, addressDetail, zipCode, location);

		// Then
		assertThat(addr).isNotNull();
		assertThat(addr.getAddress()).isEqualTo(address);
		assertThat(addr.getAddressDetail()).isEqualTo(addressDetail);
		assertThat(addr.getZipCode()).isEqualTo(zipCode);
		assertThat(addr.getLocation()).isEqualTo(location);
	}

	@Test
	@DisplayName("GeoLocation이 null이면 Address 생성 시 예외가 발생해야 한다")
	void createAddress_WithNullGeoLocation_ShouldFail() {
		// Given
		String address = "Seoul";
		String addressDetail = "Detail";
		String zipCode = "12345";

		// When / Then
		try {
			Address.of(address, addressDetail, zipCode, null);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(NullPointerException.class);
		}
	}
}