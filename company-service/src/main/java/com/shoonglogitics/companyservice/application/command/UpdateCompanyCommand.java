package com.shoonglogitics.companyservice.application.command;

import java.util.UUID;

import com.shoonglogitics.companyservice.domain.common.vo.AuthUser;
import com.shoonglogitics.companyservice.domain.company.vo.CompanyType;

import lombok.Builder;

@Builder
public record UpdateCompanyCommand(
	AuthUser authUser,
	UUID companyId,
	String name,
	String address,
	String addressDetail,
	String zipCode,
	Double latitude,
	Double longitude,
	CompanyType type
) {
}
