package com.shoonglogitics.companyservice.application.dto.company;

import java.time.LocalDateTime;
import java.util.UUID;

import com.shoonglogitics.companyservice.domain.company.entity.Company;
import com.shoonglogitics.companyservice.domain.company.vo.CompanyType;

public record CompanyResult(
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
	public static CompanyResult from(Company company) {
		return new CompanyResult(
			company.getId(),
			company.getHubId(),
			company.getName(),
			company.getAddressValue(),
			company.getAddressDetailValue(),
			company.getZipCodeValue(),
			company.getLatitude(),
			company.getLongitude(),
			company.getType(),
			company.getCreatedAt(),
			company.getUpdatedAt()
		);
	}
}