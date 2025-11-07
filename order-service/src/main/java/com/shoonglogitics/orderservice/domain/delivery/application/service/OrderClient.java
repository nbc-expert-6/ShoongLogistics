package com.shoonglogitics.orderservice.domain.delivery.application.service;

import java.util.UUID;

import com.shoonglogitics.orderservice.domain.delivery.application.dto.CreateDeliveryOrderInfo;
import com.shoonglogitics.orderservice.global.common.vo.UserRoleType;

public interface OrderClient {
	CreateDeliveryOrderInfo getOrderInfo(UUID orderId, Long userId, UserRoleType role);

}
