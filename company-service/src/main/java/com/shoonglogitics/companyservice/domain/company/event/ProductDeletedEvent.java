package com.shoonglogitics.companyservice.domain.company.event;

import java.util.UUID;

import lombok.Getter;

@Getter
public class ProductDeletedEvent extends CompanyDomainEvent {
	private Long userId;
	private UUID productId;

	public ProductDeletedEvent(Long userId, UUID productId) {
		super();
		this.userId = userId;
		this.productId = productId;
	}
}
