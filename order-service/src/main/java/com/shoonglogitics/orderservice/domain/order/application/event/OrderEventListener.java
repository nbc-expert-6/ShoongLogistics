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
		log.info("Commit 이후 동작하는 주문 생성 이벤트 수신");
		Thread current = Thread.currentThread();
		log.info("현재 스레드 이름: {}, ID: {}", current.getName(), current.getId());
	}

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
	public void handleOrderCreatedAfterRollBack(OrderCreatedEvent event) {
		log.info("RollBack 이후 동작하는 주문 생성 이벤트 수신");
		Thread current = Thread.currentThread();
		log.info("현재 스레드 이름: {}, ID: {}", current.getName(), current.getId());
	}

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void paymentCompleted(PaymentCompletedEvent event) {
		log.info("결제 완료 이벤트 수신");
		orderService.pay(event.getOrderId());
	}
}
