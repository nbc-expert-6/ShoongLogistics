package com.shoonglogitics.companyservice.infrastructure.external;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.shoonglogitics.companyservice.domain.common.vo.UserRoleType;
import com.shoonglogitics.companyservice.infrastructure.external.dto.ProductInfoFeignClientResponse;
import com.shoonglogitics.companyservice.infrastructure.security.HeaderType;
import com.shoonglogitics.companyservice.presentation.common.dto.ApiResponse;
import com.shoonglogitics.companyservice.presentation.common.dto.PageResponse;

@FeignClient(
	name = "company-service",
	contextId = "companyFeignClient",
	url = "${company-service.url}"
)
public interface CompanyFeignClient {
	@GetMapping("/api/v1/companies/products")
	ApiResponse<PageResponse<ProductInfoFeignClientResponse>> getProducts(
		@RequestParam List<UUID> categoryIds,
		@RequestHeader(HeaderType.USER_ID) Long currentUserId,
		@RequestHeader(HeaderType.USER_ROLE) UserRoleType role);

}
