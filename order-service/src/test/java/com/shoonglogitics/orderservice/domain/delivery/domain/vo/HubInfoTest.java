package com.shoonglogitics.orderservice.domain.delivery.domain.vo;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HubInfoTest {

	@Test
	@DisplayName("허브 정보 생성 시 UUID가 정상적으로 설정되어야 한다")
	void hubInfo_of_shouldSetHubIdCorrectly() {
		// Given
		UUID hubId = UUID.randomUUID();

		// When
		HubInfo hubInfo = HubInfo.of(hubId);

		// Then
		assertThat(hubInfo.getHubId()).isEqualTo(hubId);
	}

	@Test
	@DisplayName("동일 UUID로 생성된 허브 정보는 동일해야 한다")
	void hubInfo_withSameUUID_shouldHaveSameValue() {
		// Given
		UUID hubId = UUID.randomUUID();

		// When
		HubInfo hub1 = HubInfo.of(hubId);
		HubInfo hub2 = HubInfo.of(hubId);

		// Then
		assertThat(hub1.getHubId()).isEqualTo(hub2.getHubId());
	}
}