package com.shoonglogitics.companyservice.domain.company.entity;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.shoonglogitics.companyservice.domain.common.vo.AuthUser;
import com.shoonglogitics.companyservice.domain.common.vo.GeoLocation;
import com.shoonglogitics.companyservice.domain.company.vo.CompanyAddress;
import com.shoonglogitics.companyservice.domain.company.vo.CompanyType;

/**
 * 엔티티 테스트 - Company
 */
class CompanyTest {

	@Test
	@DisplayName("업체를 생성할 수 있다")
	void createCompany() {
		// Given
		UUID hubId = UUID.randomUUID();
		String name = "서울 제조 업체";
		CompanyAddress address = CompanyAddress.of(
			"서울특별시 강남구 테헤란로 123",
			"12층",
			"06234"
		);
		GeoLocation location = GeoLocation.of(37.5665, 126.9780);
		CompanyType type = CompanyType.MANUFACTURER;

		// When
		Company company = Company.create(hubId, name, address, location, type);

		// Then
		assertThat(company.getHubId()).isEqualTo(hubId);
		assertThat(company.getName()).isEqualTo(name);
		assertThat(company.getAddress()).isEqualTo(address);
		assertThat(company.getLocation()).isEqualTo(location);
		assertThat(company.getType()).isEqualTo(type);
	}

	@Test
	@DisplayName("업체 정보를 수정할 수 있다")
	void updateCompany() {
		// Given
		Company company = createTestCompany();
		AuthUser authUser = createAuthUser();

		String newName = "부산 제조 업체";
		CompanyAddress newAddress = CompanyAddress.of(
			"부산광역시 해운대구 센텀중앙로 78",
			"5층",
			"48058"
		);
		GeoLocation newLocation = GeoLocation.of(35.1796, 129.0756);
		CompanyType newType = CompanyType.RECEIVER;

		// When
		company.update(newName, newAddress, newLocation, newType);

		// Then
		assertThat(company.getName()).isEqualTo(newName);
		assertThat(company.getAddress()).isEqualTo(newAddress);
		assertThat(company.getLocation()).isEqualTo(newLocation);
		assertThat(company.getType()).isEqualTo(newType);
	}

	@Test
	@DisplayName("업체를 삭제(소프트 삭제)할 수 있다")
	void deleteCompany() {
		// Given
		Company company = createTestCompany();
		AuthUser authUser = createAuthUser();

		// When
		company.delete(authUser.getUserId());

		// Then
		assertThat(company.getDeletedAt()).isNotNull();
		assertThat(company.getDeletedBy()).isEqualTo(authUser.getUserId());
	}

	@Test
	@DisplayName("위도 정보를 조회할 수 있다")
	void getLatitude() {
		// Given
		Company company = createTestCompany();

		// When
		Double latitude = company.getLatitude();

		// Then
		assertThat(latitude).isEqualTo(37.5665);
	}

	@Test
	@DisplayName("경도 정보를 조회할 수 있다")
	void getLongitude() {
		// Given
		Company company = createTestCompany();

		// When
		Double longitude = company.getLongitude();

		// Then
		assertThat(longitude).isEqualTo(126.9780);
	}

	@Test
	@DisplayName("주소 정보를 조회할 수 있다")
	void getAddressValue() {
		// Given
		Company company = createTestCompany();

		// When
		String address = company.getAddressValue();

		// Then
		assertThat(address).isEqualTo("서울특별시 강남구 테헤란로 123");
	}

	@Test
	@DisplayName("상세 주소 정보를 조회할 수 있다")
	void getAddressDetailValue() {
		// Given
		Company company = createTestCompany();

		// When
		String addressDetail = company.getAddressDetailValue();

		// Then
		assertThat(addressDetail).isEqualTo("12층");
	}

	@Test
	@DisplayName("우편번호 정보를 조회할 수 있다")
	void getZipCodeValue() {
		// Given
		Company company = createTestCompany();

		// When
		String zipCode = company.getZipCodeValue();

		// Then
		assertThat(zipCode).isEqualTo("06234");
	}

	private Company createTestCompany() {
		UUID hubId = UUID.randomUUID();
		String name = "서울 제조 업체";
		CompanyAddress address = CompanyAddress.of(
			"서울특별시 강남구 테헤란로 123",
			"12층",
			"06234"
		);
		GeoLocation location = GeoLocation.of(37.5665, 126.9780);
		CompanyType type = CompanyType.MANUFACTURER;

		return Company.create(hubId, name, address, location, type);
	}

	private AuthUser createAuthUser() {
		return AuthUser.of(1L, "MASTER");
	}
}

