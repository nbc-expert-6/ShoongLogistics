package com.shoonglogitics.hubservice.application.event;

import com.shoonglogitics.hubservice.domain.event.HubDeactivatedEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.shoonglogitics.hubservice.domain.event.HubCreatedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class HubEventHandler {

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleHubCreated(HubCreatedEvent event) {
		//허브 캐싱 재설정
	}

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleHubDeactivated(HubDeactivatedEvent event) {
		//허브 캐싱 재설정
	}

}
