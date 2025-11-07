package com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.client.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto.FeignHubResponse;
import com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto.ListShippingRoutesRequest;
import com.shoonglogitics.orderservice.global.common.exception.ApiResponse;
import com.shoonglogitics.orderservice.global.common.vo.UserRoleType;

@FeignClient(
	name = "hub-service",
	url = "${hub-service.url}"
)
public interface HubFeignClient {
	@PostMapping("/api/v1/hubs/routes")
	ResponseEntity<ApiResponse<List<FeignHubResponse>>> getShippingRoutes(
		@RequestBody ListShippingRoutesRequest request,
		@RequestHeader("X-User-Id") Long userId, @RequestHeader("X-User-Role") UserRoleType role
	);
}
