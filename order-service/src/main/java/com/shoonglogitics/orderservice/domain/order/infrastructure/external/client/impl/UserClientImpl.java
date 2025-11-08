package com.shoonglogitics.orderservice.domain.order.infrastructure.external.client.impl;

import org.springframework.stereotype.Component;

import com.shoonglogitics.orderservice.domain.order.application.service.UserClient;
import com.shoonglogitics.orderservice.global.common.vo.UserRoleType;

@Component
public class UserClientImpl implements UserClient {
	@Override
	public void canOrder(Long userId, UserRoleType role) {
		//실제 구현
	}
}
