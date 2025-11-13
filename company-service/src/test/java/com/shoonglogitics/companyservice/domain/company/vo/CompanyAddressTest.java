package com.shoonglogitics.companyservice.domain.company.vo;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 값 객체 테스트 - CompanyAddress
 */
class CompanyAddressTest {

	@Test
	@DisplayName("유효한 주소 정보로 CompanyAddress를 생성할 수 있다")
	void createValidAddress() {
		// Given
		String address = "서울특별시 강남구 테헤란로 123";
		String addressDetail = "12층 1201호";
		String zipCode = "06234";

		// When
		CompanyAddress companyAddress = CompanyAddress.of(address, addressDetail, zipCode);

		// Then
		assertThat(companyAddress.getAddress()).isEqualTo(address);
		assertThat(companyAddress.getAddressDetail()).isEqualTo(addressDetail);
		assertThat(companyAddress.getZipCode()).isEqualTo(zipCode);
	}

	@Test
	@DisplayName("같은 주소 정보의 CompanyAddress 객체는 동일하다")
	void equality() {
		// Given
		CompanyAddress address1 = CompanyAddress.of(
			"서울특별시 강남구 테헤란로 123",
			"12층 1201호",
			"06234"
		);
		CompanyAddress address2 = CompanyAddress.of(
			"서울특별시 강남구 테헤란로 123",
			"12층 1201호",
			"06234"
		);

		// Then
		assertThat(address1).isEqualTo(address2);
		assertThat(address1.hashCode()).isEqualTo(address2.hashCode());
	}

	@Test
	@DisplayName("주소 정보 중 하나라도 다르면 동일하지 않다")
	void inequality_differentZipCode() {
		// Given
		CompanyAddress address1 = CompanyAddress.of(
			"서울특별시 강남구 테헤란로 123",
			"12층 1201호",
			"06234"
		);
		CompanyAddress address2 = CompanyAddress.of(
			"서울특별시 강남구 테헤란로 123",
			"12층 1201호",
			"06235"  // 우편번호만 다름
		);

		// Then
		assertThat(address1).isNotEqualTo(address2);
	}
}
