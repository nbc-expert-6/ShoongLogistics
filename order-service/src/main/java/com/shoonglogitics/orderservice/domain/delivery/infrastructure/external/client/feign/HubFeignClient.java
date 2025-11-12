package com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.client.feign;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto.FeignRouteResponse;
import com.shoonglogitics.orderservice.global.common.exception.ApiResponse;
import com.shoonglogitics.orderservice.global.common.vo.UserRoleType;

@FeignClient(
	name = "hub-service",
	url = "${hub-service.url}"
)
public interface HubFeignClient {
	@PostMapping("/api/v1/hub-routes/optimal-route")
	ResponseEntity<ApiResponse<FeignRouteResponse>> getShippingRoutes(
		@RequestParam UUID departureId,
		@RequestParam UUID arrivalId,
		@RequestHeader("X-User-Id") Long userId, @RequestHeader("X-User-Role") UserRoleType role
	);
}
