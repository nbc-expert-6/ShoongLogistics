package com.shoonglogitics.orderservice.domain.order.domain.entity;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.shoonglogitics.orderservice.domain.order.domain.vo.Money;
import com.shoonglogitics.orderservice.domain.order.domain.vo.ProductInfo;
import com.shoonglogitics.orderservice.domain.order.domain.vo.Quantity;

class OrderItemTest {

	@ParameterizedTest
	@ValueSource(ints = {1, 2, 5, 10})
	@DisplayName("Quentity가 1 이상이면 OrderItem이 정상 생성되어야 한다")
	void createOrderItem_withValidQuentity_shouldBeCreated(int quantity) {
		// Given
		ProductInfo productInfo = ProductInfo.of(UUID.randomUUID(), Money.of(new BigDecimal("1000")));
		Quantity quentity = Quantity.of(quantity);

		// When
		OrderItem orderItem = OrderItem.create(productInfo, quentity);

		// Then
		assertThat(orderItem).isNotNull();
		assertThat(orderItem.getQuantity().getValue()).isEqualTo(quantity);
	}

	@ParameterizedTest
	@ValueSource(ints = {0, -1, -10})
	@DisplayName("Quentity가 1 미만이면 OrderItem 생성 시 예외가 발생해야 한다")
	void createOrderItem_withInvalidQuentity_shouldThrowException(int quantity) {
		// Given
		ProductInfo productInfo = ProductInfo.of(UUID.randomUUID(), Money.of(new BigDecimal("1000")));

		// When & Then
		assertThatThrownBy(() -> OrderItem.create(productInfo, Quantity.of(quantity)))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("주문 수량은 최소 1개 이상이어야 합니다");
	}

	@ParameterizedTest
	@ValueSource(strings = {"1000", "0", "999999"})
	@DisplayName("calculateTotalPrice는 가격과 수량 곱한 값을 반환해야 한다")
	void calculateTotalPrice_withValidPriceAndQuantity_shouldReturnCorrectTotal(String priceStr) {
		// Given
		BigDecimal price = new BigDecimal(priceStr);
		ProductInfo productInfo = ProductInfo.of(UUID.randomUUID(), Money.of(price));
		Quantity quantity = Quantity.of(3);
		OrderItem orderItem = OrderItem.create(productInfo, quantity);

		// When
		Money totalPrice = orderItem.calculateTotalPrice();

		// Then
		assertThat(totalPrice.getAmount()).isEqualByComparingTo(price.multiply(BigDecimal.valueOf(3)));
	}

	@ParameterizedTest
	@ValueSource(strings = {"-1", "-100", "-0.01"})
	@DisplayName("ProductInfo 가격이 음수이면 OrderItem 생성 시 예외가 발생해야 한다")
	void createOrderItem_withNegativePrice_shouldThrowException(String priceStr) {
		// Given
		BigDecimal price = new BigDecimal(priceStr);

		// When & Then
		assertThatThrownBy(() -> ProductInfo.of(UUID.randomUUID(), Money.of(price)))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("0보다 작을 수 없습니다");
	}
}