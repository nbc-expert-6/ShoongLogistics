package com.shoonglogitics.orderservice.domain.order.domain.entity;

import java.util.UUID;

import com.shoonglogitics.orderservice.domain.order.domain.event.OrderDomainEvent;

import lombok.Getter;

@Getter
public class OrderCancledEvent extends OrderDomainEvent {
	private final UUID orderId;

	public OrderCancledEvent(UUID id) {
		this.orderId = id;
	}
}
