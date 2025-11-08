package com.shoonglogitics.orderservice.domain.delivery.domain.event;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public abstract class DeliveryDomainEvent {
	private final LocalDateTime occurredAt;

	protected DeliveryDomainEvent() {
		this.occurredAt = LocalDateTime.now();
	}
}