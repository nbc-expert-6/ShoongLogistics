package com.shoonglogitics.companyservice.domain.company.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CompanyType {
	MANUFACTURER("생산업체"),
	RECEIVER("공급업체");

	private final String description;
}
