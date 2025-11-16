package com.shoonglogitics.orderservice.domain.order.application;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.shoonglogitics.orderservice.domain.order.application.command.CreateOrderCommand;
import com.shoonglogitics.orderservice.domain.order.application.command.CreateOrderItemCommand;
import com.shoonglogitics.orderservice.domain.order.application.event.OrderEventListener;
import com.shoonglogitics.orderservice.domain.order.domain.event.OrderCreatedEvent;
import com.shoonglogitics.orderservice.domain.order.domain.repository.OrderRepository;
import com.shoonglogitics.orderservice.domain.common.vo.UserRoleType;

@SpringBootTest
@ActiveProfiles("test")
class TransactionalListenerTest {

	@Autowired
	private OrderService orderService;

	@MockBean
	private OrderEventListener orderEventListener;

	@Autowired
	private OrderRepository orderRepository;

	@BeforeEach
	void cleanUp() {
		orderRepository.deleteAll();
	}

	//요청에 쓸 공용 객체
	private CreateOrderCommand createCommand() {
		CreateOrderItemCommand itemCommand = CreateOrderItemCommand.builder()
			.productId(UUID.randomUUID())
			.price(BigDecimal.valueOf(1000))
			.quantity(10)
			.build();

		return CreateOrderCommand.builder()
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
	}

	@Test
	@DisplayName("트랜잭션 롤백 시 After RollBack 시점 리스너가 동작한다.")
	void whenTransactionRollback_AfterRollBackListenerShouldBeCalled() {
		// given
		CreateOrderCommand command = createCommand();

		// when & then
		// 주문 save호출 후 예외가 발생하여 commit되지 않고 rollback 됨
		assertThatThrownBy(() -> orderService.createOrderRollBack(command))
			.isInstanceOf(IllegalArgumentException.class);

		// 트랜잭션이 롤백되었으므로, 저장된 주문이 없음
		assertThat(orderRepository.getAllOrders().isEmpty()).isTrue();

		// 커밋 시점 이벤트 리스너가 실행되지 않고
		verify(orderEventListener, never())
			.handleOrderCreatedAfterCommit(any(OrderCreatedEvent.class));
		// 롤백 시점 이벤트 리스너가 동작
		verify(orderEventListener, times(1))
			.handleOrderCreatedAfterRollBack(any(OrderCreatedEvent.class));
	}

	@Test
	@DisplayName("트랜잭션 커밋 성공 시 After Commit 시점 리스너가 동작한다.")
	void whenTransactionRollback_AfterCommitListenerShouldBeCalled() {
		// given
		CreateOrderCommand command = createCommand();

		// when
		// 트랜잭션이 커밋되므로 주문 저장
		orderService.createOrder(command);
		// then
		// 주문이 1개 저장되어있고
		assertThat(orderRepository.getAllOrders().size()).isEqualTo(1);
		// 커밋 이후 시점 이벤트 리스너가 동작
		verify(orderEventListener, times(1))
			.handleOrderCreatedAfterCommit(any(OrderCreatedEvent.class));
		// 롤백 시점 이벤트 리스너는 동작하지 않음
		verify(orderEventListener, never())
			.handleOrderCreatedAfterRollBack(any(OrderCreatedEvent.class));
	}
}