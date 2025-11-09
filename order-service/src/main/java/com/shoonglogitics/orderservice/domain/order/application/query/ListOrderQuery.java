package com.shoonglogitics.orderservice.domain.order.application.query;

import com.shoonglogitics.orderservice.global.common.dto.PageRequest;
import com.shoonglogitics.orderservice.global.common.vo.UserRoleType;

public record ListOrderQuery(
	Long userId,
	UserRoleType role,
	PageRequest pageRequest
) {
	public static ListOrderQuery from(Long userId, UserRoleType role, PageRequest pageRequest) {
		return new ListOrderQuery(userId, role, pageRequest);
	}
}
