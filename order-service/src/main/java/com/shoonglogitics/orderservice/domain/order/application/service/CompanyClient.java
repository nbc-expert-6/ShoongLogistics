package com.shoonglogitics.orderservice.domain.order.application.service;

import java.util.List;
import java.util.UUID;

import com.shoonglogitics.orderservice.domain.order.application.command.CreateOrderItemCommand;
import com.shoonglogitics.orderservice.domain.order.application.dto.OrderItemInfo;
import com.shoonglogitics.orderservice.domain.order.application.dto.StockInfo;
import com.shoonglogitics.orderservice.domain.common.vo.UserRoleType;

public interface CompanyClient {
	void validateItems(List<CreateOrderItemCommand> orderItems);

	void decreaseStock(UUID productId, Integer quantity, Long userId, UserRoleType role);

	StockInfo getStockInfo(UUID productId, Long userId, UserRoleType role);

	OrderItemInfo getOrderItemInfos(UUID supplierCompanyId, UUID productId, Long userId, UserRoleType role);
}
