package com.shoonglogitics.orderservice.domain.order.infrastructure.external.client.impl;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.shoonglogitics.orderservice.domain.order.application.service.DeliveryClient;
import com.shoonglogitics.orderservice.domain.order.infrastructure.external.client.feign.DeliveryFeignClient;
import com.shoonglogitics.orderservice.domain.order.infrastructure.external.dto.FeignCreateDeliveryRequest;
import com.shoonglogitics.orderservice.domain.order.infrastructure.external.dto.FeignDeliveryResponse;
import com.shoonglogitics.orderservice.global.common.exception.ApiResponse;
import com.shoonglogitics.orderservice.global.common.vo.UserRoleType;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeliveryClientImpl implements DeliveryClient {

	private final DeliveryFeignClient deliveryFeignClient;

	@Override
	public void createDelivery(UUID orderId, Long userId, UserRoleType role) {
		ResponseEntity<ApiResponse<FeignDeliveryResponse>> response = deliveryFeignClient.createDelivery(
			FeignCreateDeliveryRequest.from(orderId), userId, role);

	}
}
