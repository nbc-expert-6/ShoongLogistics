package com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.client.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.shoonglogitics.orderservice.domain.delivery.application.service.HubClient;
import com.shoonglogitics.orderservice.domain.delivery.application.service.dto.RoutesInfo;
import com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.client.feign.HubFeignClient;
import com.shoonglogitics.orderservice.domain.delivery.infrastructure.external.mapper.HubMapper;
import com.shoonglogitics.orderservice.global.common.vo.UserRoleType;

import lombok.RequiredArgsConstructor;

@Component("hubClientImpl")
@RequiredArgsConstructor
public class HubClientImpl implements HubClient {

	private final HubFeignClient hubFeignClient;

	@Override
	public List<RoutesInfo> getDeliveryRoutesInfo(UUID departureHubId, UUID destinationHubId, Long userId,
		UserRoleType role) {
		// ResponseEntity<ApiResponse<List<FeignHubResponse>>> response = hubFeignClient.getShippingRoutes(
		// 	ListShippingRoutesRequest.from(departureHubId, destinationHubId), userId, role
		// );
		// return HubMapper.toCreateDeliveryRoutesInfo(response.getBody().data());
		return HubMapper.toCreateDeliveryRoutesInfoDummy();
	}
}
