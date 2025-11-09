package com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.mapper;

import com.shoonglogitics.orderservice.domain.delivery.application.service.dto.CompanyInfo;
import com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto.FeignCompanyResponse;

public final class CompanyMapper {

	private CompanyMapper() {
	}

	public static CompanyInfo toCreateDeliveryCompanyInfo(FeignCompanyResponse response) {
		if (response == null) {
			return null;
		}
		return CompanyInfo.from(response);
	}
}
