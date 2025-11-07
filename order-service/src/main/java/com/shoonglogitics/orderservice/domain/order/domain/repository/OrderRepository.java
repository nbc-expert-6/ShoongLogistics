package com.shoonglogitics.orderservice.domain.order.domain.repository;

import java.util.Optional;
import java.util.UUID;

import com.shoonglogitics.orderservice.domain.order.domain.entity.Order;

public interface OrderRepository {
	Order save(Order order);

	Optional<Order> findById(UUID orderId);
}
