package com.shoonglogitics.orderservice.domain.order.application.service;

import java.util.UUID;

import com.shoonglogitics.orderservice.domain.order.application.service.dto.DeliveryInfo;
import com.shoonglogitics.orderservice.domain.common.vo.UserRoleType;

public interface DeliveryClient {
	void createDelivery(UUID order, Long userId, UserRoleType role);

	void updateDelivery(UUID orderId, String deliveryRequest, Long userId, UserRoleType role);

	DeliveryInfo getDelivery(UUID orderId, Long userId, UserRoleType role);

	void deleteDelivery(UUID orderId, Long userId, UserRoleType role);
}
