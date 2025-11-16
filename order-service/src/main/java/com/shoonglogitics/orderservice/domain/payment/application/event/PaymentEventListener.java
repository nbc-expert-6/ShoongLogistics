package com.shoonglogitics.orderservice.domain.payment.application.event;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.shoonglogitics.orderservice.domain.order.domain.event.OrderCreatedEvent;
import com.shoonglogitics.orderservice.domain.payment.application.service.PaymentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "PaymentEventListener")
@Component
@RequiredArgsConstructor
public class PaymentEventListener {

	private final PaymentService paymentService;

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleOrderCreatedEvent(OrderCreatedEvent event) {
		log.info("주문 생성 이벤트 수신 - 결제 처리 시작");
		paymentService.processPayment(event.getOrderId(), event.getProductId(),
			event.getTotalPrice(), event.getPrice(), event.getQuantity());
		log.info("결제 처리 완료");
	}
}
