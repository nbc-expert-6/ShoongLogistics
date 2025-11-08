package com.shoonglogitics.orderservice.domain.order.infrastructure.external.client.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.shoonglogitics.orderservice.domain.order.application.command.CreateOrderItemCommand;
import com.shoonglogitics.orderservice.domain.order.application.service.CompanyClient;
import com.shoonglogitics.orderservice.domain.order.domain.entity.OrderItem;

import lombok.RequiredArgsConstructor;

@Component("orderCompanyClientImpl")
@RequiredArgsConstructor
public class CompanyClientImpl implements CompanyClient {
	@Override
	public void validateItems(List<CreateOrderItemCommand> orderItems) {
		//실제 구현

	}

	@Override
	public void decreaseStock(List<OrderItem> orderItems) {
		//재고 감소 요청
	}
}
