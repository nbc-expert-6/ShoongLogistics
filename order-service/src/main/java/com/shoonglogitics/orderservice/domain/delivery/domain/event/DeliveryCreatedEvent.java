package com.shoonglogitics.orderservice.domain.delivery.domain.event;

import java.util.UUID;

public class DeliveryCreatedEvent extends DeliveryDomainEvent {
	private final UUID shipperId;

	public DeliveryCreatedEvent(UUID shipperId) {
		this.shipperId = shipperId;
	}
}
