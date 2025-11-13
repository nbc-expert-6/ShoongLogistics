package com.shoonglogitics.orderservice.domain.delivery.domain.vo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ShipperInfoTest {

	@Test
	@DisplayName("ShipperInfo 생성 시 모든 필드가 정상적으로 설정되어야 한다")
	void shipperInfo_of_shouldSetAllFieldsCorrectly() {
		// Given
		String name = "홍길동";
		String phone = "010-1234-5678";
		String slackId = "hong123";

		// When
		ShipperInfo shipper = ShipperInfo.of(1L, name, phone, slackId);

		// Then
		assertThat(shipper.getShipperId()).isEqualTo(1L);
		assertThat(shipper.getShipperName()).isEqualTo(name);
		assertThat(shipper.getShipperPhoneNumber()).isEqualTo(phone);
		assertThat(shipper.getShipperSlackId()).isEqualTo(slackId);
	}

	@Test
	@DisplayName("ShipperInfo 업데이트 시 null이 아닌 값만 변경되어야 한다")
	void shipperInfo_update_shouldChangeOnlyNonNullFields() {
		// Given
		ShipperInfo shipper = ShipperInfo.of(1L, "홍길동", "010-1234-5678", "hong123");
		String newName = "김철수";
		String newPhone = null; // 변경하지 않음
		String newSlackId = "kim456";

		// When
		shipper.update(1L, newName, newPhone, newSlackId);

		// Then
		assertThat(shipper.getShipperId()).isEqualTo(1L);
		assertThat(shipper.getShipperName()).isEqualTo(newName);
		assertThat(shipper.getShipperPhoneNumber()).isEqualTo("010-1234-5678"); // 기존 값 유지
		assertThat(shipper.getShipperSlackId()).isEqualTo(newSlackId);
	}

	@Test
	@DisplayName("ShipperInfo 업데이트 시 모든 값이 null이면 기존 값이 유지되어야 한다")
	void shipperInfo_update_withAllNull_shouldKeepOriginalValues() {
		// Given
		String name = "홍길동";
		String phone = "010-1234-5678";
		String slackId = "hong123";
		ShipperInfo shipper = ShipperInfo.of(1L, name, phone, slackId);

		// When
		shipper.update(null, null, null, null);

		// Then
		assertThat(shipper.getShipperId()).isEqualTo(1L);
		assertThat(shipper.getShipperName()).isEqualTo(name);
		assertThat(shipper.getShipperPhoneNumber()).isEqualTo(phone);
		assertThat(shipper.getShipperSlackId()).isEqualTo(slackId);
	}
}