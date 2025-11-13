package com.shoonglogitics.companyservice.infrastructure.external;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.shoonglogitics.companyservice.domain.common.vo.UserRoleType;
import com.shoonglogitics.companyservice.infrastructure.external.dto.CreateStockFeignClientRequest;
import com.shoonglogitics.companyservice.infrastructure.external.dto.CreateStockFeignClientResponse;
import com.shoonglogitics.companyservice.infrastructure.external.dto.StockInfoFeignClientResponse;
import com.shoonglogitics.companyservice.infrastructure.security.HeaderType;
import com.shoonglogitics.companyservice.presentation.common.dto.ApiResponse;

@FeignClient(
	name = "company-service",
	contextId = "stockFeignClient",
	url = "${company-service.url}"
)
public interface StockFeignClient {
	/**
	 * 재고 등록 API
	 */
	@PostMapping("/api/v1/stocks")
	ApiResponse<CreateStockFeignClientResponse> createStock(@RequestBody CreateStockFeignClientRequest createStockRequest,
		@RequestHeader(HeaderType.USER_ID) Long currentUserId,
		@RequestHeader(HeaderType.USER_ROLE) UserRoleType role);

	@DeleteMapping("/api/v1/stocks/{stockId}")
	ApiResponse<Void> deleteStock(@PathVariable UUID stockId,
		@RequestHeader(HeaderType.USER_ID) Long currentUserId,
		@RequestHeader(HeaderType.USER_ROLE) UserRoleType role);

	@GetMapping("/api/v1/stocks/product/{productId}")
	ApiResponse<StockInfoFeignClientResponse> getStockByProductId(@PathVariable UUID productId,
		@RequestHeader(HeaderType.USER_ID) Long currentUserId,
		@RequestHeader(HeaderType.USER_ROLE) UserRoleType role);
}
