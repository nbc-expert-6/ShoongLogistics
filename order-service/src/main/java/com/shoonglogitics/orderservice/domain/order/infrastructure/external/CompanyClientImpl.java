package com.shoonglogitics.orderservice.domain.order.infrastructure.external;

import java.util.List;

import org.springframework.stereotype.Component;

import com.shoonglogitics.orderservice.domain.order.application.command.CreateOrderItemCommand;
import com.shoonglogitics.orderservice.domain.order.application.service.CompanyClient;

@Component
public class CompanyClientImpl implements CompanyClient {
	@Override
	public void validateItems(List<CreateOrderItemCommand> orderItems) {
		//실제 구현

	}
}
