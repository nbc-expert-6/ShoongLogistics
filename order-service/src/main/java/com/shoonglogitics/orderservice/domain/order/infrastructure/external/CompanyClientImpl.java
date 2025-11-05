package com.shoonglogitics.orderservice.domain.order.infrastructure.external;

import java.util.List;

import org.springframework.stereotype.Component;

import com.shoonglogitics.orderservice.domain.order.application.service.CompanyClient;
import com.shoonglogitics.orderservice.domain.order.domain.entity.OrderItem;

@Component
public class CompanyClientImpl implements CompanyClient {
	@Override
	public void validateItems(List<OrderItem> orderItems) {
		//실제 구현

	}
}
