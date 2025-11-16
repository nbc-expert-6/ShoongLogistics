package com.shoonglogitics.orderservice.domain.product.application.event;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.shoonglogitics.orderservice.domain.payment.domain.event.PaymentCompletedEvent;
import com.shoonglogitics.orderservice.domain.product.application.service.StockService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "StockEventListener")
@Component
@RequiredArgsConstructor
public class StockEventListener {

	private final StockService stockService;

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handlePaymentCompleted(PaymentCompletedEvent event) {
		log.info("결제 완료 이벤트 수신 - 재고 차감 시작");
		stockService.decreaseStock(event.getProductId(), event.getQuantity());
		log.info("재고 차감 완료");

	}
}
