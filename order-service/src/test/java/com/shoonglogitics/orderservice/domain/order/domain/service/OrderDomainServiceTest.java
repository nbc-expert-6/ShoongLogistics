package com.shoonglogitics.orderservice.domain.order.domain.service;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.shoonglogitics.orderservice.domain.order.domain.entity.OrderItem;
import com.shoonglogitics.orderservice.domain.order.domain.vo.Money;
import com.shoonglogitics.orderservice.domain.order.domain.vo.ProductInfo;
import com.shoonglogitics.orderservice.domain.order.domain.vo.Quentity;

class OrderDomainServiceTest {

	private final OrderDomainService service = new OrderDomainService();

	@Test
	@DisplayName("주문 항목과 총 금액이 일치하면 검증이 통과해야 한다")
	void validateOrder_withValidItems_shouldPass() {
		// Given
		OrderItem item1 = OrderItem.create(ProductInfo.of(UUID.randomUUID(), Money.of(new BigDecimal("1000"))),
			Quentity.of(1));
		OrderItem item2 = OrderItem.create(ProductInfo.of(UUID.randomUUID(), Money.of(new BigDecimal("2000"))),
			Quentity.of(1));
		Money total = Money.of(new BigDecimal("3000"));
		List<OrderItem> items = List.of(item1, item2);

		// When & Then
		assertThatCode(() -> service.validateOrder(items, total))
			.doesNotThrowAnyException();
	}

	@Test
	@DisplayName("주문 항목이 비어있으면 예외가 발생해야 한다")
	void validateOrder_withEmptyItems_shouldThrowException() {
		// Given
		List<OrderItem> items = List.of();
		Money total = Money.of(BigDecimal.ZERO);

		// When & Then
		assertThatThrownBy(() -> service.validateOrder(items, total))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("주문 항목은 최소 1개 이상이어야 합니다");
	}

	@Test
	@DisplayName("총 주문 금액과 항목 합계가 일치하지 않으면 예외가 발생해야 한다")
	void validateOrder_withMismatchedTotal_shouldThrowException() {
		// Given
		OrderItem item1 = OrderItem.create(ProductInfo.of(UUID.randomUUID(), Money.of(new BigDecimal("1000"))),
			Quentity.of(1));
		OrderItem item2 = OrderItem.create(ProductInfo.of(UUID.randomUUID(), Money.of(new BigDecimal("2000"))),
			Quentity.of(1));
		Money total = Money.of(new BigDecimal("5000")); // 틀린 금액
		List<OrderItem> items = List.of(item1, item2);

		// When & Then
		assertThatThrownBy(() -> service.validateOrder(items, total))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("주문 금액 합계");
	}
}