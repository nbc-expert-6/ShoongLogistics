package com.shoonglogitics.companyservice.domain.company.event;

import java.util.UUID;

import lombok.Getter;

@Getter
public class ProductDeletedEvent extends CompanyDomainEvent {
	private Long userId;
	private UUID stockId;

	public ProductDeletedEvent(Long userId, UUID stockId) {
		super();
		this.userId = userId;
		this.stockId = stockId;
	}
}
