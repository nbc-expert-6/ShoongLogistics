package com.shoonglogitics.orderservice.domain.delivery.application.service;

import java.util.List;
import java.util.UUID;

import com.shoonglogitics.orderservice.domain.delivery.application.dto.CreateDeliveryShipperInfo;
import com.shoonglogitics.orderservice.global.common.vo.UserRoleType;

public interface UserClient {
	List<CreateDeliveryShipperInfo> getShippers(UUID hubId, Long aLong, UserRoleType role);
}
