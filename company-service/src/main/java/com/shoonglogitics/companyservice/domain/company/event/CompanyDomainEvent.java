package com.shoonglogitics.companyservice.domain.company.event;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public abstract class CompanyDomainEvent {
	private final LocalDateTime occurredAt;

	protected CompanyDomainEvent() {
		this.occurredAt = LocalDateTime.now();
	}
}
