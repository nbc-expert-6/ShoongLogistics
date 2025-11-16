package com.shoonglogitics.orderservice.domain.delivery.application.service;

import java.util.UUID;

import com.shoonglogitics.orderservice.domain.delivery.application.service.dto.OrderInfo;
import com.shoonglogitics.orderservice.domain.common.vo.UserRoleType;

public interface OrderClient {
	OrderInfo getOrderInfo(UUID orderId, Long userId, UserRoleType role);

}
