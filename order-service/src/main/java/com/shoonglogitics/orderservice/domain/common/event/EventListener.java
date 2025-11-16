package com.shoonglogitics.orderservice.domain.common.event;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "AsyncTransactionalEventListener")
public abstract class EventListener<T extends DomainEvent> {

	public void handleEvent(T event) {
		try {
			log.info("이벤트 처리 시작 - Type: {}, AggregateId: {}",
				event.getClass().getSimpleName(),
				event.getAggregateId()
			);

			// 하위 리스너가 구현
			processEvent(event);

			log.info("이벤트 처리 완료 - Type: {}", event.getClass().getSimpleName());
		} catch (Exception e) {
			log.error("이벤트 처리 실패 - Type: {}, Error: {}",
				event.getClass().getSimpleName(),
				e.getMessage()
			);
			handleException(event, e);
		}
	}

	// 서비스 실행 로직 구현 필요
	protected abstract void processEvent(T event);

	protected void handleException(T event, Exception e) {
		log.info("이벤트 실패 기본 예외처리 동작");
	}
}
