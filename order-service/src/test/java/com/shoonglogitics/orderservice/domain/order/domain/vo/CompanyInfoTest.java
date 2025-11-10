package com.shoonglogitics.orderservice.domain.order.domain.vo;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CompanyInfoTest {

	@Test
	@DisplayName("회사 ID와 이름으로 CompanyInfo를 생성할 수 있어야 한다")
	void companyInfo_withValidFields_ShouldBeCreated() {
		// Given
		UUID companyId = UUID.randomUUID();
		String companyName = "테스트회사";

		// When
		CompanyInfo info = CompanyInfo.of(companyId, companyName);

		// Then
		assertThat(info.getCompanyId()).isEqualTo(companyId);
		assertThat(info.getCompanyName()).isEqualTo(companyName);
	}
}