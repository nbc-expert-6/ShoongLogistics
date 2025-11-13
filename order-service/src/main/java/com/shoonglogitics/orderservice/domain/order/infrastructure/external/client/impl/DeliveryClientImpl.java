package com.shoonglogitics.orderservice.domain.order.infrastructure.external.client.impl;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.shoonglogitics.orderservice.domain.order.application.service.DeliveryClient;
import com.shoonglogitics.orderservice.domain.order.application.service.dto.DeliveryInfo;
import com.shoonglogitics.orderservice.domain.order.infrastructure.external.client.feign.DeliveryFeignClient;
import com.shoonglogitics.orderservice.domain.order.infrastructure.external.dto.FeignCreateDeliveryRequest;
import com.shoonglogitics.orderservice.domain.order.infrastructure.external.dto.FeignDeliveryResponse;
import com.shoonglogitics.orderservice.domain.order.infrastructure.external.dto.FeignUpdateDeliveryRequest;
import com.shoonglogitics.orderservice.domain.order.infrastructure.external.mapper.DeliveryMapper;
import com.shoonglogitics.orderservice.global.common.exception.ApiResponse;
import com.shoonglogitics.orderservice.global.common.vo.UserRoleType;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeliveryClientImpl implements DeliveryClient {

	private final DeliveryFeignClient deliveryFeignClient;

	//주문 생성시 배송 생성 요청
	@Override
	public void createDelivery(UUID orderId, Long userId, UserRoleType role) {
		ResponseEntity<ApiResponse<FeignDeliveryResponse>> response = deliveryFeignClient.createDelivery(
			FeignCreateDeliveryRequest.from(orderId), userId, role);

	}

	//배송 요청사항 변경 시 배송에서 요청사항 변경 요청
	@Override
	public void updateDelivery(UUID deliveryId, String deliveryRequest, Long userId, UserRoleType role) {
		ResponseEntity<ApiResponse<FeignDeliveryResponse>> response = deliveryFeignClient.updateDelivery(
			deliveryId, FeignUpdateDeliveryRequest.from(deliveryRequest), userId, role
		);
	}

	//주문 Id로 배송정보 조회 요청
	@Override
	public DeliveryInfo getDelivery(UUID orderId, Long userId, UserRoleType role) {
		ResponseEntity<ApiResponse<FeignDeliveryResponse>> response = deliveryFeignClient.getDelivery(
			orderId, userId, role
		);
		return DeliveryMapper.toDeliveryInfo(response.getBody().data());
	}

	//주문이 취소되면 관련 배송 삭제 요청
	@Override
	public void deleteDelivery(UUID deliveryId, Long userId, UserRoleType role) {
		ResponseEntity<ApiResponse<FeignDeliveryResponse>> response = deliveryFeignClient.deleteDelivery(
			deliveryId, userId, role
		);
	}
}
