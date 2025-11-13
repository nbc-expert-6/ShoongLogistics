package com.shoonglogitics.orderservice.domain.order.domain.event;

import java.util.UUID;

import lombok.Getter;

@Getter
public class OrderCreatedEvent extends OrderDomainEvent {

	private final UUID orderId;

	public OrderCreatedEvent(UUID orderId) {
		this.orderId = orderId;
	}
}
