package com.shoonglogitics.orderservice.domain.delivery.domain.entity;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.shoonglogitics.orderservice.domain.delivery.domain.vo.DeliveryEstimate;
import com.shoonglogitics.orderservice.domain.delivery.domain.vo.DeliveryStatus;
import com.shoonglogitics.orderservice.domain.delivery.domain.vo.HubInfo;
import com.shoonglogitics.orderservice.domain.delivery.domain.vo.ShipperInfo;

class DeliveryRouteTest {

	@Test
	@DisplayName("DeliveryRoute 생성 시 모든 필수 필드가 정상적으로 설정되어야 한다")
	void deliveryRoute_create_shouldSetAllFieldsCorrectly() {
		// Given
		ShipperInfo shipper = ShipperInfo.of(UUID.randomUUID(), "홍길동", "010-1234-5678", "hong123");
		HubInfo departureHub = HubInfo.of(UUID.randomUUID());
		HubInfo arrivalHub = HubInfo.of(UUID.randomUUID());
		int sequence = 1;
		DeliveryEstimate estimate = DeliveryEstimate.of(1000L, 30);

		// When
		DeliveryRoute route = DeliveryRoute.create(shipper, departureHub, arrivalHub, sequence, estimate);

		// Then
		assertThat(route.getShipperInfo()).isEqualTo(shipper);
		assertThat(route.getDepartureHubId()).isEqualTo(departureHub);
		assertThat(route.getArrivalHubId()).isEqualTo(arrivalHub);
		assertThat(route.getSequence()).isEqualTo(sequence);
		assertThat(route.getEstimate()).isEqualTo(estimate);
		assertThat(route.getStatus()).isEqualTo(DeliveryStatus.HUB_WAITING);
	}

	@Test
	@DisplayName("DeliveryRoute 생성 시 필수 값이 null이면 예외가 발생해야 한다")
	void deliveryRoute_create_withNullFields_shouldThrowException() {
		// Given
		ShipperInfo shipper = ShipperInfo.of(UUID.randomUUID(), "홍길동", "010-1234-5678", "hong123");
		HubInfo hub = HubInfo.of(UUID.randomUUID());
		DeliveryEstimate estimate = DeliveryEstimate.of(1000L, 30);

		// When&Then
		assertThatThrownBy(() -> DeliveryRoute.create(null, hub, hub, 1, estimate))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("배송 담당자");

		assertThatThrownBy(() -> DeliveryRoute.create(shipper, null, hub, 1, estimate))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("출발 허브");

		assertThatThrownBy(() -> DeliveryRoute.create(shipper, hub, null, 1, estimate))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("도착 허브");

		assertThatThrownBy(() -> DeliveryRoute.create(shipper, hub, hub, null, estimate))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("순번");

		assertThatThrownBy(() -> DeliveryRoute.create(shipper, hub, hub, 0, estimate))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("순번");

		assertThatThrownBy(() -> DeliveryRoute.create(shipper, hub, hub, 1, null))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("예상 배송 정보");
	}
}