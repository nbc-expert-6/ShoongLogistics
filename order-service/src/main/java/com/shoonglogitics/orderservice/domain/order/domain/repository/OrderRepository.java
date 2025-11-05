package com.shoonglogitics.orderservice.domain.order.domain.repository;

import java.util.Optional;

import com.shoonglogitics.orderservice.domain.order.domain.entity.Order;

public interface OrderRepository {
	Optional<Order> save(Order order);
}
