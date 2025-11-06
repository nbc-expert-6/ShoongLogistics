package com.shoonglogitics.companyservice.application.command;

import java.util.UUID;

import com.shoonglogitics.companyservice.domain.company.vo.CompanyType;
import com.shoonglogitics.companyservice.presentation.company.common.dto.PageRequest;

import lombok.Builder;

@Builder
public record GetCompaniesCommand(
	UUID hubId,
	String name,
	CompanyType type,
	PageRequest pageRequest
) {
}
