package com.shoonglogitics.orderservice.domain.delivery.domain.event;

import java.util.List;
import java.util.UUID;

import lombok.Getter;

@Getter
public class DeliveryCreatedEvent extends DeliveryDomainEvent {
	private final List<UUID> shippers;

	public DeliveryCreatedEvent(List<UUID> shippers) {
		this.shippers = shippers;
	}
}
