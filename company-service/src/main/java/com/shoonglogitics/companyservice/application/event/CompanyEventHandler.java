package com.shoonglogitics.companyservice.application.event;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.shoonglogitics.companyservice.application.service.UserClient;
import com.shoonglogitics.companyservice.domain.company.event.CompanyDeletedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompanyEventHandler {
	private final UserClient userClient;

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleCompanyDeleted(CompanyDeletedEvent event) {
		userClient.deleteCompanyManager(event.getAuthUser(), event.getCompanyId());
	}

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleCompanyUpdated(CompanyDeletedEvent event) {
		userClient.deleteCompanyManager(event.getAuthUser(), event.getCompanyId());
	}
}
