package com.shoonglogitics.orderservice.domain.order.domain.vo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderStatusTest {

	@Test
	@DisplayName("주문 대기 상태는 결제 완료나 취소로 전환할 수 있어야 한다")
	void orderStatus_pending_canTransitionToPaidOrCancelled_ShouldBeTrue() {
		// Given
		OrderStatus status = OrderStatus.PENDING;

		// When & Then
		assertThat(status.canTransitionTo(OrderStatus.PAID)).isTrue();
		assertThat(status.canTransitionTo(OrderStatus.CANCELLED)).isTrue();
		assertThat(status.canTransitionTo(OrderStatus.SHIPPED)).isFalse();
		assertThat(status.canTransitionTo(OrderStatus.DELIVERED)).isFalse();
	}

	@Test
	@DisplayName("결제 완료 상태는 배송 중이나 취소로 전환할 수 있어야 한다")
	void orderStatus_paid_canTransitionToShippedOrCancelled_ShouldBeTrue() {
		// Given
		OrderStatus status = OrderStatus.PAID;

		// When & Then
		assertThat(status.canTransitionTo(OrderStatus.SHIPPED)).isTrue();
		assertThat(status.canTransitionTo(OrderStatus.CANCELLED)).isTrue();
		assertThat(status.canTransitionTo(OrderStatus.PENDING)).isFalse();
		assertThat(status.canTransitionTo(OrderStatus.DELIVERED)).isFalse();
	}

	@Test
	@DisplayName("배송 중 상태는 배송 완료로만 전환할 수 있어야 한다")
	void orderStatus_shipped_canTransitionToDeliveredOnly_ShouldBeTrue() {
		// Given
		OrderStatus status = OrderStatus.SHIPPED;

		// When & Then
		assertThat(status.canTransitionTo(OrderStatus.DELIVERED)).isTrue();
		assertThat(status.canTransitionTo(OrderStatus.PAID)).isFalse();
		assertThat(status.canTransitionTo(OrderStatus.CANCELLED)).isFalse();
	}

	@Test
	@DisplayName("배송 완료 상태는 더 이상 전환할 수 없어야 한다")
	void orderStatus_delivered_cannotTransitionToAnyStatus_ShouldBeFalse() {
		// Given
		OrderStatus status = OrderStatus.DELIVERED;

		// When & Then
		assertThat(status.canTransitionTo(OrderStatus.PENDING)).isFalse();
		assertThat(status.canTransitionTo(OrderStatus.PAID)).isFalse();
		assertThat(status.canTransitionTo(OrderStatus.SHIPPED)).isFalse();
		assertThat(status.canTransitionTo(OrderStatus.CANCELLED)).isFalse();
	}

	@Test
	@DisplayName("주문 대기 상태는 취소와 수정 가능해야 한다")
	void orderStatus_pending_canBeCancelledOrUpdated_ShouldBeTrue() {
		// Given
		OrderStatus status = OrderStatus.PENDING;

		// When & Then
		assertThat(status.canBeCancelled()).isTrue();
		assertThat(status.canBeUpdatable()).isTrue();
	}

	@Test
	@DisplayName("배송 중 상태는 취소나 수정이 불가능해야 한다")
	void orderStatus_shipped_cannotBeCancelledOrUpdated_ShouldBeFalse() {
		// Given
		OrderStatus status = OrderStatus.SHIPPED;

		// When & Then
		assertThat(status.canBeCancelled()).isFalse();
		assertThat(status.canBeUpdatable()).isFalse();
	}

	@Test
	@DisplayName("주문 대기 상태는 결제 가능해야 한다")
	void orderStatus_pending_canBePaid_ShouldBeTrue() {
		// Given
		OrderStatus status = OrderStatus.PENDING;

		// When & Then
		assertThat(status.canBePaid()).isTrue();
		assertThat(OrderStatus.PAID.canBePaid()).isFalse();
	}

	@Test
	@DisplayName("결제 완료 상태는 배송 가능해야 한다")
	void orderStatus_paid_canBeShipped_ShouldBeTrue() {
		// Given
		OrderStatus status = OrderStatus.PAID;

		// When & Then
		assertThat(status.canBeShipped()).isTrue();
		assertThat(OrderStatus.SHIPPED.canBeShipped()).isFalse();
	}
}