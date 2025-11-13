package com.shoonglogitics.orderservice.domain.order.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import com.shoonglogitics.orderservice.domain.order.domain.entity.Order;
import com.shoonglogitics.orderservice.domain.order.domain.repository.OrderRepository;
import com.shoonglogitics.orderservice.global.common.dto.PageRequest;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepository {

	private final JpaOrderRepository jpaOrderRepository;

	@Override
	public Order save(Order order) {
		return jpaOrderRepository.save(order);
	}

	@Override
	public Optional<Order> findById(UUID orderId) {
		return jpaOrderRepository.findById(orderId);
	}

	@Override
	public Page<Order> getOrdersByMaster(PageRequest pageRequest) {
		return jpaOrderRepository.findAll(pageRequest.toPageable());
	}

	@Override
	public Page<Order> getOrdersByUserId(Long userId, PageRequest pageRequest) {
		return jpaOrderRepository.findAllByUserId(userId, pageRequest.toPageable());
	}
}
