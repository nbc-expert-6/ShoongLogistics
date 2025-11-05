package com.shoonglogitics.orderservice.domain.order.infrastructure.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.shoonglogitics.orderservice.domain.order.domain.entity.Order;
import com.shoonglogitics.orderservice.domain.order.domain.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepository {

	private final JpaOrderRepository jpaOrderRepository;

	@Override
	public Optional<Order> save(Order order) {
		return Optional.of(jpaOrderRepository.save(order));
	}
}
