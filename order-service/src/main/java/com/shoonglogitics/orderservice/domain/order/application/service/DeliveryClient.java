package com.shoonglogitics.orderservice.domain.order.application.service;

import java.util.UUID;

import com.shoonglogitics.orderservice.global.common.vo.UserRoleType;

public interface DeliveryClient {
	void createDelivery(UUID order, Long userId, UserRoleType role);

}
