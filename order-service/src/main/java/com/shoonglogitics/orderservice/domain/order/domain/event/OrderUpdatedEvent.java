package com.shoonglogitics.orderservice.domain.order.domain.event;

import java.util.UUID;

import lombok.Getter;

@Getter
public class OrderUpdatedEvent extends OrderDomainEvent {
	private final UUID orderId;
	private final String deliveryRequest;

	public OrderUpdatedEvent(UUID id, String deliveryRequest) {
		this.orderId = id;
		this.deliveryRequest = deliveryRequest;
	}
}
