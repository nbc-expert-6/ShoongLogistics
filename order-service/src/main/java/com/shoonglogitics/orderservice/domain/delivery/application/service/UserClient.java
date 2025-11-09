package com.shoonglogitics.orderservice.domain.delivery.application.service;

import java.util.List;
import java.util.UUID;

import com.shoonglogitics.orderservice.domain.delivery.application.service.dto.ShipperInfo;
import com.shoonglogitics.orderservice.global.common.vo.UserRoleType;

public interface UserClient {
	List<ShipperInfo> getShippers(UUID hubId, Long aLong, UserRoleType role);

	void changeShippersIsAvailable(List<UUID> shippers, Long userId, UserRoleType role);
}
