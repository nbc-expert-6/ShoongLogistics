package com.shoonglogitics.orderservice.domain.delivery.domain.vo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DeliveryStatusTest {

	@Test
	@DisplayName("허브 대기 상태는 허브 이동중 상태로만 전환 가능해야 한다")
	void hubWaiting_canTransitionToHubTransit_ShouldBeTrue() {
		DeliveryStatus status = DeliveryStatus.HUB_WAITING;

		assertThat(status.canTransitionTo(DeliveryStatus.HUB_TRANSIT)).isTrue();
		assertThat(status.canTransitionTo(DeliveryStatus.ARRIVAL_HUB_ARRIVED)).isFalse();
		assertThat(status.canTransitionTo(DeliveryStatus.DESTINATION_HUB_ARRIVED)).isFalse();
		assertThat(status.canTransitionTo(DeliveryStatus.IN_DELIVERY)).isFalse();
		assertThat(status.canTransitionTo(DeliveryStatus.DELIVERED)).isFalse();
	}

	@Test
	@DisplayName("허브 이동중 상태는 허브 도착 또는 목적지 허브 도착 상태로 전환 가능해야 한다")
	void hubTransit_canTransitionToHubArrived_ShouldBeTrue() {
		DeliveryStatus status = DeliveryStatus.HUB_TRANSIT;

		assertThat(status.canTransitionTo(DeliveryStatus.ARRIVAL_HUB_ARRIVED)).isTrue();
		assertThat(status.canTransitionTo(DeliveryStatus.DESTINATION_HUB_ARRIVED)).isTrue();
		assertThat(status.canTransitionTo(DeliveryStatus.HUB_WAITING)).isFalse();
		assertThat(status.canTransitionTo(DeliveryStatus.IN_DELIVERY)).isFalse();
		assertThat(status.canTransitionTo(DeliveryStatus.DELIVERED)).isFalse();
	}

	@Test
	@DisplayName("허브 도착 상태는 다른 상태로 전환할 수 없어야 한다")
	void arrivalHubArrived_cannotTransitionToAnyStatus_ShouldBeFalse() {
		DeliveryStatus status = DeliveryStatus.ARRIVAL_HUB_ARRIVED;

		assertThat(status.canTransitionTo(DeliveryStatus.HUB_TRANSIT)).isFalse();
		assertThat(status.canTransitionTo(DeliveryStatus.DESTINATION_HUB_ARRIVED)).isFalse();
		assertThat(status.canTransitionTo(DeliveryStatus.IN_DELIVERY)).isFalse();
		assertThat(status.canTransitionTo(DeliveryStatus.DELIVERED)).isFalse();
	}

	@Test
	@DisplayName("목적지 허브 도착 상태는 배송 중 상태로만 전환 가능해야 한다")
	void destinationHubArrived_canTransitionToInDelivery_ShouldBeTrue() {
		DeliveryStatus status = DeliveryStatus.DESTINATION_HUB_ARRIVED;

		assertThat(status.canTransitionTo(DeliveryStatus.IN_DELIVERY)).isTrue();
		assertThat(status.canTransitionTo(DeliveryStatus.HUB_TRANSIT)).isFalse();
		assertThat(status.canTransitionTo(DeliveryStatus.ARRIVAL_HUB_ARRIVED)).isFalse();
		assertThat(status.canTransitionTo(DeliveryStatus.DELIVERED)).isFalse();
	}

	@Test
	@DisplayName("배송 중 상태는 배송 완료 상태로만 전환 가능해야 한다")
	void inDelivery_canTransitionToDelivered_ShouldBeTrue() {
		DeliveryStatus status = DeliveryStatus.IN_DELIVERY;

		assertThat(status.canTransitionTo(DeliveryStatus.DELIVERED)).isTrue();
		assertThat(status.canTransitionTo(DeliveryStatus.HUB_WAITING)).isFalse();
		assertThat(status.canTransitionTo(DeliveryStatus.HUB_TRANSIT)).isFalse();
	}

	@Test
	@DisplayName("배송 완료 상태는 더 이상 전환할 수 없어야 한다")
	void delivered_cannotTransitionToAnyStatus_ShouldBeFalse() {
		DeliveryStatus status = DeliveryStatus.DELIVERED;

		assertThat(status.canTransitionTo(DeliveryStatus.HUB_WAITING)).isFalse();
		assertThat(status.canTransitionTo(DeliveryStatus.HUB_TRANSIT)).isFalse();
		assertThat(status.canTransitionTo(DeliveryStatus.IN_DELIVERY)).isFalse();
	}

	@Test
	@DisplayName("각 상태에서 취소 가능 여부 검증")
	void testCancellable() {
		assertThat(DeliveryStatus.HUB_WAITING.canBeCancelled()).isTrue();
		assertThat(DeliveryStatus.HUB_TRANSIT.canBeCancelled()).isFalse();
		assertThat(DeliveryStatus.ARRIVAL_HUB_ARRIVED.canBeCancelled()).isFalse();
		assertThat(DeliveryStatus.DESTINATION_HUB_ARRIVED.canBeCancelled()).isFalse();
		assertThat(DeliveryStatus.IN_DELIVERY.canBeCancelled()).isFalse();
		assertThat(DeliveryStatus.DELIVERED.canBeCancelled()).isFalse();
	}

	@Test
	@DisplayName("각 상태의 설명이 올바르게 설정되어 있어야 한다")
	void testDescription() {
		assertThat(DeliveryStatus.HUB_WAITING.getDescription()).isEqualTo("허브 대기중");
		assertThat(DeliveryStatus.HUB_TRANSIT.getDescription()).isEqualTo("허브 이동중");
		assertThat(DeliveryStatus.ARRIVAL_HUB_ARRIVED.getDescription()).isEqualTo("허브 도착");
		assertThat(DeliveryStatus.DESTINATION_HUB_ARRIVED.getDescription()).isEqualTo("목적지 허브 도착");
		assertThat(DeliveryStatus.IN_DELIVERY.getDescription()).isEqualTo("배송중");
		assertThat(DeliveryStatus.DELIVERED.getDescription()).isEqualTo("배송완료");
	}
}