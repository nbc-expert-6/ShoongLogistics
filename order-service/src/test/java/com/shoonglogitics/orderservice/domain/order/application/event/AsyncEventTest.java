package com.shoonglogitics.orderservice.domain.order.application.event;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.shoonglogitics.orderservice.domain.order.application.OrderService;
import com.shoonglogitics.orderservice.domain.order.application.command.CreateOrderCommand;
import com.shoonglogitics.orderservice.domain.order.application.command.CreateOrderItemCommand;
import com.shoonglogitics.orderservice.domain.order.domain.event.OrderCreatedEvent;
import com.shoonglogitics.orderservice.global.common.vo.UserRoleType;

@SpringBootTest
@ActiveProfiles("test")
class AsyncEventTest {
	private static final Logger log = LoggerFactory.getLogger(AsyncEventTest.class);
	@Autowired
	private OrderService orderService;

	@MockBean
	private OrderEventListener orderEventListener;

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
	@DisplayName("이벤트가 비동기로 처리된다")
	void events_ShouldBeProcessedAsynchronously() throws InterruptedException {
		// given
		CreateOrderCommand command = createCommand();
		String mainThread = Thread.currentThread().getName();
		log.info("메인 스레드: {}", mainThread);

		// when
		orderService.createOrder(command);

		// 비동기 처리를 위한 대기
		Thread.sleep(1000);

		// then
		verify(orderEventListener, times(1))
			.handleOrderCreatedAfterCommit(any(OrderCreatedEvent.class));
	}

	@Test
	@DisplayName("비동기 처리 중 예외가 발생해도 메인 로직에 영향을 주지 않는다")
	void whenAsyncEventThrowsException_MainFlowShouldNotBeAffected() {
		// given
		CreateOrderCommand command = createCommand();

		// 이벤트 처리중 예외 강제 발생
		doAnswer(invocation -> {
			throw new RuntimeException("주문 생성 이벤트 처리 실패");
		}).when(orderEventListener)
			.handleOrderCreatedAfterCommit(any(OrderCreatedEvent.class));

		// when & then
		// 비동기 작업에서 예외가 발생해도, 메인 로직(주문 생성)은 성공
		assertThatCode(() -> orderService.createOrder(command))
			.doesNotThrowAnyException();
		// 비동기 이벤트가 호출됐는지 검증
		verify(orderEventListener, times(1))
			.handleOrderCreatedAfterCommit(any(OrderCreatedEvent.class));
	}
}