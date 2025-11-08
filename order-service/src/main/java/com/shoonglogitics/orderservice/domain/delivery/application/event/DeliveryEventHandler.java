package com.shoonglogitics.orderservice.domain.delivery.application.event;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.shoonglogitics.orderservice.domain.order.domain.event.OrderCreatedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryEventHandler {
	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleOrderCreated(OrderCreatedEvent event) {
		//배정한 배송 담당자 배송불가능 하도록 수정 요청
		log.info("Received order created event: {}", event);
	}
}
