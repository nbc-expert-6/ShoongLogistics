package com.shoonglogitics.orderservice.domain.delivery.domain.event;

import java.util.List;

import lombok.Getter;

@Getter
public class DeliveryCreatedEvent extends DeliveryDomainEvent {
	private final List<Long> shippers;

	public DeliveryCreatedEvent(List<Long> shippers) {
		this.shippers = shippers;
	}
}
