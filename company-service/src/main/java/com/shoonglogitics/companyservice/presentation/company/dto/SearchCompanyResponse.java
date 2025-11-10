package com.shoonglogitics.companyservice.presentation.company.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.shoonglogitics.companyservice.application.dto.CompanyResult;
import com.shoonglogitics.companyservice.domain.company.vo.CompanyType;

public record SearchCompanyResponse(
	UUID id,
	UUID hubId,
	String name,
	CompanyType type,
	LocalDateTime createdAt
) {
	public static SearchCompanyResponse from(CompanyResult result) {
		return new SearchCompanyResponse(
			result.id(),
			result.hubId(),
			result.name(),
			result.type(),
			result.createdAt()
		);
	}
}
