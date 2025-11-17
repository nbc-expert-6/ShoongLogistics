package com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.client.feign;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto.FeignCompanyResponse;
import com.shoonglogitics.orderservice.domain.common.exception.ApiResponse;
import com.shoonglogitics.orderservice.domain.common.vo.UserRoleType;

@FeignClient(
	name = "company-service",
	url = "${company-service.url}"
)
public interface CompanyFeignClient {
	@GetMapping("/api/v1/companies/{companyId}")
	ResponseEntity<ApiResponse<FeignCompanyResponse>> getCompany(@PathVariable("companyId") UUID companyId,
		@RequestHeader("X-User-Id") Long userId, @RequestHeader("X-User-Role") UserRoleType role);
}
