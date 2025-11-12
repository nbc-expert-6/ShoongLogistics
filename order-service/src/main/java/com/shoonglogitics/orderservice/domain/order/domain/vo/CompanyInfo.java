package com.shoonglogitics.orderservice.domain.order.domain.vo;

import java.util.UUID;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CompanyInfo {

	private UUID companyId;
	private String companyName;

	public static CompanyInfo of(UUID companyId, String companyName) {
		CompanyInfo companyInfo = new CompanyInfo();
		companyInfo.companyId = companyId;
		companyInfo.companyName = companyName;
		return companyInfo;
	}
}
