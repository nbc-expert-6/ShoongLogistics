package com.shoonglogitics.companyservice.domain.company.event;

import java.util.UUID;

import com.shoonglogitics.companyservice.domain.common.vo.AuthUser;

public class CompanyUpdatedEvent extends CompanyDomainEvent{
	private UUID companyId;
	private AuthUser authUser;

	public CompanyUpdatedEvent(UUID companyId, AuthUser authUser) {
		super();
		this.companyId = companyId;
		this.authUser = authUser;
	}
}
