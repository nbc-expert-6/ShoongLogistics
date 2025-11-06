package com.shoonglogitics.orderservice.domain.order.application.service;

import java.util.List;

import com.shoonglogitics.orderservice.domain.order.application.command.CreateOrderItemCommand;
import com.shoonglogitics.orderservice.domain.order.domain.entity.OrderItem;

public interface CompanyClient {
	void validateItems(List<CreateOrderItemCommand> orderItems);

	void decreaseStock(List<OrderItem> orderItems);
}
