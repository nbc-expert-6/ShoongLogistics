package com.shoonglogitics.orderservice.domain.order.domain.entity;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.shoonglogitics.orderservice.domain.order.domain.vo.Address;
import com.shoonglogitics.orderservice.domain.order.domain.vo.CompanyInfo;
import com.shoonglogitics.orderservice.domain.order.domain.vo.Money;
import com.shoonglogitics.orderservice.domain.order.domain.vo.OrderStatus;
import com.shoonglogitics.orderservice.domain.order.domain.vo.ProductInfo;
import com.shoonglogitics.orderservice.domain.order.domain.vo.Quantity;

class OrderTest {

	@Test
	@DisplayName("Order 생성 시 필수 값이 모두 주어지면 정상적으로 생성되어야 한다")
	void createOrder_withValidValues_shouldBeCreated() {
		// Given
		CompanyInfo receiver = CompanyInfo.of(UUID.randomUUID(), "ReceiverCo");
		CompanyInfo supplier = CompanyInfo.of(UUID.randomUUID(), "SupplierCo");
		Address address = Address.of("서울시", "강남구 123", "12345",
			null); // GeoLocation 테스트용 null 가능
		ProductInfo productInfo = ProductInfo.of(UUID.randomUUID(), Money.of(new BigDecimal("1000")));
		OrderItem orderItem = OrderItem.create(productInfo, Quantity.of(2));
		Money totalPrice = Money.of(new BigDecimal("2000"));

		// When
		Order order = Order.create(1L, receiver, supplier, "request", "deliveryRequest",
			totalPrice, address, List.of(orderItem));

		// Then
		assertThat(order).isNotNull();
		assertThat(order.getOrderItems()).hasSize(1);
		assertThat(order.getTotalPrice().getAmount()).isEqualByComparingTo(totalPrice.getAmount());
		assertThat(order.getStatus()).isEqualTo(OrderStatus.PENDING);
	}

	@Test
	@DisplayName("Order 생성 시 receiver와 supplier가 동일하면 예외가 발생해야 한다")
	void createOrder_withSameReceiverAndSupplier_shouldThrowException() {
		// Given
		UUID companyId = UUID.randomUUID();
		CompanyInfo receiver = CompanyInfo.of(companyId, "SameCo");
		CompanyInfo supplier = CompanyInfo.of(companyId, "SameCo");
		Address address = Address.of("서울시", "강남구 123", "12345", null);
		ProductInfo productInfo = ProductInfo.of(UUID.randomUUID(), Money.of(new BigDecimal("1000")));
		OrderItem orderItem = OrderItem.create(productInfo, Quantity.of(1));
		Money totalPrice = Money.of(new BigDecimal("1000"));

		// When & Then
		assertThatThrownBy(() -> Order.create(1L, receiver, supplier, "request", "deliveryRequest",
			totalPrice, address, List.of(orderItem)))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("공급업체와 수령업체가 동일할 수 없습니다");
	}

	@Test
	@DisplayName("Order 상태가 PENDING일 때 PAID로 변경 가능해야 한다")
	void changeStatus_fromPendingToPaid_shouldSucceed() {
		// Given
		CompanyInfo receiver = CompanyInfo.of(UUID.randomUUID(), "ReceiverCo");
		CompanyInfo supplier = CompanyInfo.of(UUID.randomUUID(), "SupplierCo");
		Address address = Address.of("서울시", "강남구 123", "12345", null);
		ProductInfo productInfo = ProductInfo.of(UUID.randomUUID(), Money.of(new BigDecimal("1000")));
		OrderItem orderItem = OrderItem.create(productInfo, Quantity.of(1));
		Money totalPrice = Money.of(new BigDecimal("1000"));
		Order order = Order.create(1L, receiver, supplier, "request", "deliveryRequest",
			totalPrice, address, List.of(orderItem));

		// When
		order.changeStatus(OrderStatus.PAID);

		// Then
		assertThat(order.getStatus()).isEqualTo(OrderStatus.PAID);
	}

	@Test
	@DisplayName("Order 상태가 DELIVERED일 때 수정 시 예외가 발생해야 한다")
	void updateOrder_whenDelivered_shouldThrowException() {
		// Given
		CompanyInfo receiver = CompanyInfo.of(UUID.randomUUID(), "ReceiverCo");
		CompanyInfo supplier = CompanyInfo.of(UUID.randomUUID(), "SupplierCo");
		Address address = Address.of("서울시", "강남구 123", "12345", null);
		ProductInfo productInfo = ProductInfo.of(UUID.randomUUID(), Money.of(new BigDecimal("1000")));
		OrderItem orderItem = OrderItem.create(productInfo, Quantity.of(1));
		Money totalPrice = Money.of(new BigDecimal("1000"));
		Order order = Order.create(1L, receiver, supplier, "request", "deliveryRequest",
			totalPrice, address, List.of(orderItem));
		order.changeStatus(OrderStatus.PAID);
		order.changeStatus(OrderStatus.SHIPPED);
		order.changeStatus(OrderStatus.DELIVERED);

		// When & Then
		assertThatThrownBy(() -> order.update("newRequest", "newDeliveryRequest"))
			.isInstanceOf(IllegalStateException.class)
			.hasMessageContaining("주문을 수정할 수 없는 상태입니다");
	}

	@Test
	@DisplayName("Order 상태가 CANCELLED일 때 삭제 시 예외가 발생해야 한다")
	void deleteOrder_whenCancelled_shouldThrowException() {
		// Given
		CompanyInfo receiver = CompanyInfo.of(UUID.randomUUID(), "ReceiverCo");
		CompanyInfo supplier = CompanyInfo.of(UUID.randomUUID(), "SupplierCo");
		Address address = Address.of("서울시", "강남구 123", "12345", null);
		ProductInfo productInfo = ProductInfo.of(UUID.randomUUID(), Money.of(new BigDecimal("1000")));
		OrderItem orderItem = OrderItem.create(productInfo, Quantity.of(1));
		Money totalPrice = Money.of(new BigDecimal("1000"));
		Order order = Order.create(1L, receiver, supplier, "request", "deliveryRequest",
			totalPrice, address, List.of(orderItem));
		order.changeStatus(OrderStatus.CANCELLED);

		// When & Then
		assertThatThrownBy(() -> order.delete(1L))
			.isInstanceOf(IllegalStateException.class)
			.hasMessageContaining("주문을 취소할 수 없는 상태입니다");
	}
}