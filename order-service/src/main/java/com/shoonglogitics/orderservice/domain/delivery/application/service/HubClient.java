package com.shoonglogitics.orderservice.domain.delivery.application.service;

import java.util.List;
import java.util.UUID;

import com.shoonglogitics.orderservice.domain.delivery.application.dto.CreateDeliveryRoutesInfo;
import com.shoonglogitics.orderservice.global.common.vo.UserRoleType;

public interface HubClient {
	List<CreateDeliveryRoutesInfo> getDeliveryRoutesInfo(
		UUID departureHubId, UUID destinationHubId, Long userId, UserRoleType role
	);
}
