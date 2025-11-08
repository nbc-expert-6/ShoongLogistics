package com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.client.impl;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.shoonglogitics.orderservice.domain.delivery.application.service.OrderClient;
import com.shoonglogitics.orderservice.domain.delivery.application.service.dto.CreateDeliveryOrderInfo;
import com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.client.feign.OrderFeignClient;
import com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto.FeignOrderResponse;
import com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.mapper.OrderMapper;
import com.shoonglogitics.orderservice.global.common.exception.ApiResponse;
import com.shoonglogitics.orderservice.global.common.vo.UserRoleType;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderClientImpl implements OrderClient {

	private final OrderFeignClient orderFeignClient;

	@Override
	public CreateDeliveryOrderInfo getOrderInfo(UUID orderId, Long userId, UserRoleType role) {
		ResponseEntity<ApiResponse<FeignOrderResponse>> response = orderFeignClient.getOrder(orderId, userId,
			role);
		return OrderMapper.toCreateDeliveryOrderInfo(response.getBody().data());
	}
}
