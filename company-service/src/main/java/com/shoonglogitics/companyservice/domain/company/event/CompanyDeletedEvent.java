package com.shoonglogitics.companyservice.domain.company.event;

import lombok.Getter;

@Getter
public class CompanyDeletedEvent extends CompanyDomainEvent {
	private Long userId;

	public CompanyDeletedEvent(Long userId) {
		super();
		this.userId = userId;
	}
}
