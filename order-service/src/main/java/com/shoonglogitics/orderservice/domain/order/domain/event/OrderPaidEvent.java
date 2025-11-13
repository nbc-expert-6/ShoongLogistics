package com.shoonglogitics.orderservice.domain.order.domain.event;

import java.util.UUID;

import lombok.Getter;

@Getter
public class OrderPaidEvent {
	private final UUID OrderId;

	public OrderPaidEvent(UUID id) {
		this.OrderId = id;
	}
}
