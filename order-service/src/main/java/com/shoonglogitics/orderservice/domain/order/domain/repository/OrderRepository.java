package com.shoonglogitics.orderservice.domain.order.domain.repository;

import com.shoonglogitics.orderservice.domain.order.domain.entity.Order;

public interface OrderRepository {
	Order save(Order order);
}
