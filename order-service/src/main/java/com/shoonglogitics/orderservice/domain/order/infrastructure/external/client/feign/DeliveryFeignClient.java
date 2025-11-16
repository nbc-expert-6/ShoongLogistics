package com.shoonglogitics.orderservice.domain.order.infrastructure.external.client.feign;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.shoonglogitics.orderservice.domain.order.infrastructure.external.dto.FeignCreateDeliveryRequest;
import com.shoonglogitics.orderservice.domain.order.infrastructure.external.dto.FeignDeliveryResponse;
import com.shoonglogitics.orderservice.domain.order.infrastructure.external.dto.FeignUpdateDeliveryRequest;
import com.shoonglogitics.orderservice.domain.common.exception.ApiResponse;
import com.shoonglogitics.orderservice.domain.common.vo.UserRoleType;

@FeignClient(name = "delivery-service", url = "${delivery-service.url}")
public interface DeliveryFeignClient {
	@PostMapping("/api/v1/deliveries")
	ResponseEntity<ApiResponse<FeignDeliveryResponse>> createDelivery(@RequestBody FeignCreateDeliveryRequest request,
		@RequestHeader("X-User-Id") Long userId, @RequestHeader("X-User-Role") UserRoleType role);

	@GetMapping("/api/v1/deliveries/orders/{orderId}")
	ResponseEntity<ApiResponse<FeignDeliveryResponse>> getDelivery(@PathVariable("orderId") UUID orderId,
		@RequestHeader("X-User-Id") Long userId, @RequestHeader("X-User-Role") UserRoleType role);

	@PutMapping("/api/v1/deliveries/{deliveryId}")
	ResponseEntity<ApiResponse<FeignDeliveryResponse>> updateDelivery(@PathVariable("deliveryId") UUID deliveryId,
		@RequestBody FeignUpdateDeliveryRequest request, @RequestHeader("X-User-Id") Long userId,
		@RequestHeader("X-User-Role") UserRoleType role);

	@DeleteMapping("/api/v1/deliveries/{deliveryId}")
	ResponseEntity<ApiResponse<FeignDeliveryResponse>> deleteDelivery(@PathVariable("deliveryId") UUID deliveryId,
		@RequestHeader("X-User-Id") Long userId, @RequestHeader("X-User-Role") UserRoleType role);
}
