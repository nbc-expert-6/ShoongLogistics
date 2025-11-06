package com.shoonglogitics.companyservice.presentation.company.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.shoonglogitics.companyservice.application.dto.CompanyResult;
import com.shoonglogitics.companyservice.domain.company.vo.CompanyType;

public record FindCompanyResponse(
	UUID id,
	UUID hubId,
	String name,
	String address,
	String addressDetail,
	String zipCode,
	Double latitude,
	Double longitude,
	CompanyType type,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {
	public static FindCompanyResponse from(CompanyResult result) {
		return new FindCompanyResponse(
			result.id(),
			result.hubId(),
			result.name(),
			result.address(),
			result.addressDetail(),
			result.zipCode(),
			result.latitude(),
			result.longitude(),
			result.type(),
			result.createdAt(),
			result.updatedAt()
		);
	}
}
