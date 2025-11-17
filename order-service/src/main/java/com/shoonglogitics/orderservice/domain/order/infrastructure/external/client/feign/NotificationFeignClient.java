package com.shoonglogitics.orderservice.domain.order.infrastructure.external.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.shoonglogitics.orderservice.domain.order.infrastructure.external.dto.FeignCreateDeliveryRequest;
import com.shoonglogitics.orderservice.domain.order.infrastructure.external.dto.FeignDeliveryResponse;
import com.shoonglogitics.orderservice.domain.common.exception.ApiResponse;
import com.shoonglogitics.orderservice.domain.common.vo.UserRoleType;

@FeignClient(name = "notification-service", url = "${notification-service.url}")
public interface NotificationFeignClient {
	@PostMapping("/api/v1/deliveries")
	ResponseEntity<ApiResponse<FeignDeliveryResponse>> createDelivery(@RequestBody FeignCreateDeliveryRequest request,
		@RequestHeader("X-User-Id") Long userId, @RequestHeader("X-User-Role") UserRoleType role);
}
