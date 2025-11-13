package com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.client.feign;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto.FeignOrderResponse;
import com.shoonglogitics.orderservice.global.common.exception.ApiResponse;
import com.shoonglogitics.orderservice.global.common.vo.UserRoleType;

@FeignClient(
	name = "order-service",
	url = "${order-service.url}"
)
public interface OrderFeignClient {
	@GetMapping("/api/v1/orders/{orderId}")
	ResponseEntity<ApiResponse<FeignOrderResponse>> getOrder(@PathVariable("orderId") UUID orderId,
		@RequestHeader("X-User-Id") Long userId, @RequestHeader("X-User-Role") UserRoleType role);
}
