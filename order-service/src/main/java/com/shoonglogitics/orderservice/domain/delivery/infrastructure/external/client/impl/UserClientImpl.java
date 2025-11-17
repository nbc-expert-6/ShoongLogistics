package com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.client.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.shoonglogitics.orderservice.domain.delivery.application.service.UserClient;
import com.shoonglogitics.orderservice.domain.delivery.application.service.dto.ShipperInfo;
import com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.client.feign.UserFeignClient;
import com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.dto.FeignUserResponse;
import com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.mapper.UserMapper;
import com.shoonglogitics.orderservice.domain.common.exception.ApiResponse;
import com.shoonglogitics.orderservice.domain.common.vo.UserRoleType;

import lombok.RequiredArgsConstructor;

@Component("deliveryUserClient")
@RequiredArgsConstructor
public class UserClientImpl implements UserClient {

	private final UserFeignClient userFeignClient;

	@Override
	public List<ShipperInfo> getShippers(UUID hubId, Long userId, UserRoleType role) {
		//Todo: 실제 엔드포인트로 변경

		ResponseEntity<ApiResponse<List<FeignUserResponse>>> response = userFeignClient.getInternalUsers(hubId, null,
			userId,
			role);
		return UserMapper.toCreateDeliveryShipperInfo(response.getBody().data());
	}

	//넘겨받은 배송 담당자들의 상태 변경 요청
	@Override
	public void changeShippersIsAvailable(List<Long> shippers, Long userId, UserRoleType role) {
		//Todo: 실제 수정 엔드포인트 호출

	}
}
