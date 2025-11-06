package com.shoonglogitics.orderservice.domain.order.application.service;

import java.util.List;

import com.shoonglogitics.orderservice.domain.order.application.command.CreateOrderItemCommand;

public interface CompanyClient {
	void validateItems(List<CreateOrderItemCommand> orderItems);
}
