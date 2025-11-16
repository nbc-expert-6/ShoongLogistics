package com.shoonglogitics.orderservice.domain.order.application.event;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.shoonglogitics.orderservice.domain.order.application.OrderService;
import com.shoonglogitics.orderservice.domain.order.domain.event.OrderCreatedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "OrderEventListner")
@Component
@RequiredArgsConstructor
public class OrderEventListener {

	private final OrderService orderService;

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleOrderCreatedAfterCommit(OrderCreatedEvent event) {
		log.info("Commit 이후 동작하는 주문 생성 이벤트 수신 - 주문 ID: {}, 상품 ID: {}, 수량: {}",
			event.getOrderId(),
			event.getProductId(),
			event.getQuantity()
		);
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
	public void handleOrderCreatedAfterRollBack(OrderCreatedEvent event) {
		log.info("RollBack 이후 동작하는 주문 생성 이벤트 수신 - 주문 ID: {}, 상품 ID: {}, 수량: {}",
			event.getOrderId(),
			event.getProductId(),
			event.getQuantity()
		);
	}
}
