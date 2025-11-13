package com.shoonglogitics.orderservice.domain.order.infrastructure.external.client.feign;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.shoonglogitics.orderservice.domain.order.infrastructure.external.dto.FeginStockDecreaseRequest;
import com.shoonglogitics.orderservice.domain.order.infrastructure.external.dto.FeignProductResponse;
import com.shoonglogitics.orderservice.domain.order.infrastructure.external.dto.FeignStockInfoResponse;
import com.shoonglogitics.orderservice.global.common.exception.ApiResponse;
import com.shoonglogitics.orderservice.global.common.vo.UserRoleType;

@FeignClient(
	name = "order-company-service",
	url = "${company-service.url}"
)
public interface CompanyFeignClient {

	@GetMapping("/api/v1/stocks/product/{productId}")
	ResponseEntity<ApiResponse<FeignStockInfoResponse>> getStockInfo(
		@PathVariable("productId") UUID productId,
		@RequestHeader("X-User-Id") Long userId, @RequestHeader("X-User-Role") UserRoleType role);

	@PostMapping("/api/v1/stocks/{stockId}/decrease")
	ResponseEntity<ApiResponse> decreaseStock(
		@PathVariable("stockId") UUID stockId, @RequestBody FeginStockDecreaseRequest request,
		@RequestHeader("X-User-Id") Long userId, @RequestHeader("X-User-Role") UserRoleType role);

	@GetMapping("/api/v1/companies/{companyId}/products/{productId}")
	ResponseEntity<ApiResponse<FeignProductResponse>> getOrderItemInfos(
		@PathVariable("companyId") UUID companyId, @PathVariable("productId") UUID productId,
		@RequestHeader("X-User-Id") Long userId, @RequestHeader("X-User-Role") UserRoleType role);
}
