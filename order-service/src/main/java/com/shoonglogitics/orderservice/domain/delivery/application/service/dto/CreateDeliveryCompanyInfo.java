package com.shoonglogitics.orderservice.domain.delivery.application.service.dto;

import java.util.UUID;

import com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto.FeignCompanyResponse;

public record CreateDeliveryCompanyInfo(
	UUID hubId
) {
	public static CreateDeliveryCompanyInfo from(FeignCompanyResponse response) {
		return new CreateDeliveryCompanyInfo(
			response.hubId()
		);
	}
}
