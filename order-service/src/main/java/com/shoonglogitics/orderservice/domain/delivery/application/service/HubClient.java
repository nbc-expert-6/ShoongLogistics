package com.shoonglogitics.orderservice.domain.delivery.application.service;

import java.util.List;
import java.util.UUID;

import com.shoonglogitics.orderservice.domain.delivery.application.service.dto.RoutesInfo;
import com.shoonglogitics.orderservice.global.common.vo.UserRoleType;

public interface HubClient {
	List<RoutesInfo> getDeliveryRoutesInfo(
		UUID departureHubId, UUID destinationHubId, Long userId, UserRoleType role
	);
}
