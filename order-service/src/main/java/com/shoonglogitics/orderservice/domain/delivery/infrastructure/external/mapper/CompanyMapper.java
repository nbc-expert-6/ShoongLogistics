package com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.mapper;

import com.shoonglogitics.orderservice.domain.delivery.application.service.dto.CreateDeliveryCompanyInfo;
import com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto.FeignCompanyResponse;

public final class CompanyMapper {

	private CompanyMapper() {
	}

	public static CreateDeliveryCompanyInfo toCreateDeliveryCompanyInfo(FeignCompanyResponse response) {
		if (response == null) {
			return null;
		}
		return CreateDeliveryCompanyInfo.from(response);
	}
}
