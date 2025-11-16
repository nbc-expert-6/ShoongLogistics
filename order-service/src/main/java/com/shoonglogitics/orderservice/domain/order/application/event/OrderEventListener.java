package com.shoonglogitics.orderservice.domain.order.application.event;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.shoonglogitics.orderservice.domain.order.application.OrderService;
import com.shoonglogitics.orderservice.domain.order.domain.event.OrderCreatedEvent;
import com.shoonglogitics.orderservice.domain.payment.domain.event.PaymentCompletedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "OrderEventListener")
@Component
@RequiredArgsConstructor
public class OrderEventListener {

	private final OrderService orderService;

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleOrderCreatedAfterCommit(OrderCreatedEvent event) {
		log.info("주문 생성 이벤트 수신 - AFTER_COMMIT 시점 테스트용");

	}

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
	public void handleOrderCreatedAfterRollBack(OrderCreatedEvent event) {
		log.info("주문 생성 이벤트 수신 - ROLLBACK 시점 테스트용");
	}

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void paymentCompleted(PaymentCompletedEvent event) {
		log.info("결제 완료 이벤트 수신 - 주문 상태 변경 시작");
		orderService.pay(event.getOrderId());
		log.info("주문 상태 변경 완료");
	}
}
