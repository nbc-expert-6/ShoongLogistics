package com.shoonglogitics.companyservice.infrastructure.external;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.shoonglogitics.companyservice.domain.common.vo.UserRoleType;
import com.shoonglogitics.companyservice.infrastructure.external.dto.ProductCategoryInfoFeignClientResponse;
import com.shoonglogitics.companyservice.infrastructure.security.HeaderType;
import com.shoonglogitics.companyservice.presentation.common.dto.ApiResponse;

@FeignClient(
	name = "company-service",
	contextId = "productCategoryFeignClient",
	url = "${company-service.url}"
)
public interface ProductCategoryFeignClient {
	@GetMapping("/api/v1/product-categories/{productCategoryId}")
	ApiResponse<ProductCategoryInfoFeignClientResponse> getProductCategory(@PathVariable("productCategoryId") UUID productCategoryId,
		@RequestHeader(HeaderType.USER_ID) Long currentUserId,
		@RequestHeader(HeaderType.USER_ROLE) UserRoleType role);

}
