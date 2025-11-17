package com.shoonglogitics.orderservice.domain.order.application.service;

import com.shoonglogitics.orderservice.domain.common.vo.UserRoleType;

public interface UserClient {
	void canOrder(Long userId, UserRoleType role);
}
