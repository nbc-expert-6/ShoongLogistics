package com.shoonglogitics.hubservice.application.event;

import com.shoonglogitics.hubservice.domain.event.HubDeactivatedEvent;
import org.springframework.cache.annotation.CacheEvict;
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
	@CacheEvict(value = "hubs", allEntries = true)
	public void handleHubCreated(HubCreatedEvent event) {
		//허브 캐싱 재설정
		log.info("허브 생성 - 캐싱 무효화: hubId={}, hubName={}", event.getHubId(), event.getName());
	}

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	@CacheEvict(value = {"hubs", "hub"}, allEntries = true)
	public void handleHubDeactivated(HubDeactivatedEvent event) {
		log.info("허브 삭제 - 캐싱 무효화: hubId={}", event.getHubId());
	}

}
