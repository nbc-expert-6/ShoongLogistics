package com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.client.impl;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.shoonglogitics.orderservice.domain.delivery.application.dto.CreateDeliveryCompanyInfo;
import com.shoonglogitics.orderservice.domain.delivery.application.service.CompanyClient;
import com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.client.feign.CompanyFeignClient;
import com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto.FeignCompanyResponse;
import com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.mapper.CompanyMapper;
import com.shoonglogitics.orderservice.global.common.exception.ApiResponse;
import com.shoonglogitics.orderservice.global.common.vo.UserRoleType;

import lombok.RequiredArgsConstructor;

@Component("deliveryCompanyClientImpl")
@RequiredArgsConstructor
public class CompanyClientImpl implements CompanyClient {

	private final CompanyFeignClient companyFeignClient;

	@Override
	public CreateDeliveryCompanyInfo getCompanyInfo(UUID uuid, Long userId, UserRoleType role) {
		ResponseEntity<ApiResponse<FeignCompanyResponse>> response = companyFeignClient.getCompany(uuid, userId, role);
		return CompanyMapper.toCreateDeliveryCompanyInfo(response.getBody().data());
	}
}
