package com.shoonglogitics.orderservice.domain.order.application.service;

import com.shoonglogitics.orderservice.global.common.vo.UserRoleType;

public interface UserClient {
	void canOrder(Long userId, UserRoleType role);
}
