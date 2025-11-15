package com.shoonglogitics.orderservice.domain.order.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.shoonglogitics.orderservice.domain.order.application.command.CreateOrderCommand;
import com.shoonglogitics.orderservice.domain.order.application.command.CreateOrderItemCommand;
import com.shoonglogitics.orderservice.domain.order.application.event.OrderEventListner;
import com.shoonglogitics.orderservice.domain.order.domain.event.OrderCreatedEvent;
import com.shoonglogitics.orderservice.global.common.vo.UserRoleType;

@SpringBootTest
@ActiveProfiles("test")
class OrderServiceEventTest {

	@Autowired
	private OrderService orderService;

	@MockBean
	private OrderEventListner orderEventListener;

	@DisplayName("주문 생성 시 OrderCreatedEvent가 발행 된다.")
	@Test
	void createOrder_ShouldPublishOrderCreatedEvent() {
		// given
		CreateOrderItemCommand itemCommand = CreateOrderItemCommand.builder()
			.productId(UUID.randomUUID())
			.price(BigDecimal.valueOf(1000))
			.quantity(10)
			.build();
		CreateOrderCommand command = CreateOrderCommand.builder()
			.userId(1L)
			.role(UserRoleType.MASTER)
			.receiverCompanyId(UUID.randomUUID())
			.receiverCompanyName("수령업체")
			.supplierCompanyId(UUID.randomUUID())
			.supplierCompanyName("공급업체")
			.address("address")
			.addressDetail("addressDetail")
			.zipCode("zipCode")
			.latitude(35.1587)
			.longitude(129.1604)
			.totalPrice(BigDecimal.valueOf(10000))
			.orderItems(List.of(itemCommand))
			.build();
		// when
		orderService.createOrder(command);
		// then
		verify(orderEventListener, times(1))
			.handleOrderCreated(any(OrderCreatedEvent.class));
	}

	@DisplayName("주문 생성 실패 시 OrderCreatedEvent가 발행되지 않는다.")
	@Test
	void createOrderFail_ShouldNotPublishOrderCreatedEvent() {
		// given
		CreateOrderItemCommand itemCommand = CreateOrderItemCommand.builder()
			.productId(UUID.randomUUID())
			.price(BigDecimal.valueOf(1000))
			.quantity(10)
			.build();
		//주문 금액과 계산된 총액의 불일치로 예외 발생
		CreateOrderCommand command = CreateOrderCommand.builder()
			.userId(1L)
			.role(UserRoleType.MASTER)
			.receiverCompanyId(UUID.randomUUID())
			.receiverCompanyName("수령업체")
			.supplierCompanyId(UUID.randomUUID())
			.supplierCompanyName("공급업체")
			.address("address")
			.addressDetail("addressDetail")
			.zipCode("zipCode")
			.latitude(35.1587)
			.longitude(129.1604)
			.totalPrice(BigDecimal.valueOf(100000))
			.orderItems(List.of(itemCommand))
			.build();
		// when & then
		assertThrows(RuntimeException.class, () -> orderService.createOrder(command));
		verify(orderEventListener, times(0))
			.handleOrderCreated(any(OrderCreatedEvent.class));
	}
}