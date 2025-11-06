package com.shoonglogitics.orderservice.domain.order.application.event;

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
public class OrderEventHandler {

	/*
	주문 생성 이벤트 처리
	- 배송 생성 요청
	 */
	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleOrderCreated(OrderCreatedEvent event) {
		//배송 생성 요청
		//최소한의 정보를 보내고 배송을 생성할 때 Order에 요청하여 정보를 받아가도록 설계

	}
}
