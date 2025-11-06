package com.shoonglogitics.companyservice.domain.company.event;

import java.util.UUID;

import com.shoonglogitics.companyservice.domain.common.vo.AuthUser;

import lombok.Getter;

@Getter
public class CompanyDeletedEvent extends CompanyDomainEvent {
	private UUID companyId;
	private AuthUser authUser;

	public CompanyDeletedEvent(UUID companyId, AuthUser authUser) {
		super();
		this.companyId = companyId;
		this.authUser = authUser;
	}
}
