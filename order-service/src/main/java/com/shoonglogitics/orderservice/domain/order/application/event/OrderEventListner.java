package com.shoonglogitics.orderservice.domain.order.application.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.shoonglogitics.orderservice.domain.order.application.OrderService;
import com.shoonglogitics.orderservice.domain.order.domain.event.OrderCreatedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "OrderEventListner")
@Component
@RequiredArgsConstructor
public class OrderEventListner {
	private final OrderService orderService;

	@EventListener
	public void handleOrderCreated(OrderCreatedEvent event) {
		log.info("주문 생성 이벤트 수신 - 주문 ID: {}, 상품 ID: {}, 수량: {}",
			event.getOrderId(),
			event.getProductId(),
			event.getQuantity()
		);
	}

}
