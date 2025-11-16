package com.shoonglogitics.orderservice.domain.common.event;

import java.util.UUID;

public interface DomainEvent {
	UUID getAggregateId();
}
