package com.shoonglogitics.companyservice.application.event;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.shoonglogitics.companyservice.application.service.StockClient;
import com.shoonglogitics.companyservice.application.service.UserClient;
import com.shoonglogitics.companyservice.domain.company.event.CompanyDeletedEvent;
import com.shoonglogitics.companyservice.domain.company.event.ProductCreatedEvent;
import com.shoonglogitics.companyservice.domain.company.event.ProductDeletedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompanyEventHandler {
	private final UserClient userClient;
	private final StockClient stockClient;

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleCompanyDeleted(CompanyDeletedEvent event) {
		userClient.deleteCompanyManager(event.getUserId());
	}

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleProductCreated(ProductCreatedEvent event) {
		stockClient.createStock(event.getProductId(), event.getUserId());
	}

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleProductDeleted(ProductDeletedEvent event) {
		stockClient.deleteStock(event.getStockId(), event.getUserId());
	}
}
