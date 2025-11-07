package com.shoonglogitics.companyservice.presentation.company.dto;

import com.shoonglogitics.companyservice.domain.company.vo.CompanyType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateCompanyRequest(
	@NotBlank
	String name,
	@NotBlank
	String address,
	@NotBlank
	String addressDetail,
	@NotBlank
	String zipCode,
	@NotNull
	Double latitude,
	@NotNull
	Double longitude,
	@NotNull
	CompanyType type
) {
}
