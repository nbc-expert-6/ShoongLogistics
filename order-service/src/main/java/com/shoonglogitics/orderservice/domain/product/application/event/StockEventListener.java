package com.shoonglogitics.orderservice.domain.product.application.event;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.shoonglogitics.orderservice.domain.common.event.EventListener;
import com.shoonglogitics.orderservice.domain.payment.domain.event.PaymentCompletedEvent;
import com.shoonglogitics.orderservice.domain.product.application.service.StockService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "StockEventListener")
@Component
@RequiredArgsConstructor
public class StockEventListener extends EventListener<PaymentCompletedEvent> {

	private final StockService stockService;

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void listen(PaymentCompletedEvent event) {
		super.handleEvent(event);
	}

	@Override
	protected void processEvent(PaymentCompletedEvent event) {
		stockService.decreaseStock(event.getProductId(), event.getQuantity());
	}
}
