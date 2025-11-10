package com.shoonglogitics.orderservice.domain.order.domain.event;

import java.util.UUID;

import lombok.Getter;

@Getter
public class OrderCancledEvent extends OrderDomainEvent {
	private final UUID orderId;

	public OrderCancledEvent(UUID id) {
		this.orderId = id;
	}
}
