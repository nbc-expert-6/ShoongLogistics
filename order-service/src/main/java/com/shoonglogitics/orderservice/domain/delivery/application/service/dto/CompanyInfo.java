package com.shoonglogitics.orderservice.domain.delivery.application.service.dto;

import java.util.UUID;

import com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto.FeignCompanyResponse;

public record CompanyInfo(
	UUID hubId
) {
	public static CompanyInfo from(FeignCompanyResponse response) {
		return new CompanyInfo(
			response.hubId()
		);
	}
}
