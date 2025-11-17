package com.shoonglogitics.orderservice.domain.common.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j(topic = "Event Publisher")
public class EventPublisher {
	private final ApplicationEventPublisher eventPublisher;

	public void publish(DomainEvent event) {
		log.info("이벤트 발행 - Type: {}, AggregateId: {}",
			event.getClass().getSimpleName(),
			event.getAggregateId()
		);
		eventPublisher.publishEvent(event);
	}
}
