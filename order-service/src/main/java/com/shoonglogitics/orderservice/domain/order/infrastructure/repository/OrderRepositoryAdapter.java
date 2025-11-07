package com.shoonglogitics.orderservice.domain.order.infrastructure.repository;

import org.springframework.stereotype.Repository;

import com.shoonglogitics.orderservice.domain.order.domain.entity.Order;
import com.shoonglogitics.orderservice.domain.order.domain.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepository {

	private final JpaOrderRepository jpaOrderRepository;

	@Override
	public Order save(Order order) {
		return jpaOrderRepository.save(order);
	}
}
