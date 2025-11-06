package com.shoonglogitics.orderservice.domain.order.domain.event;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public abstract class OrderDomainEvent {
	private final LocalDateTime occurredAt;

	protected OrderDomainEvent() {
		this.occurredAt = LocalDateTime.now();
	}
}