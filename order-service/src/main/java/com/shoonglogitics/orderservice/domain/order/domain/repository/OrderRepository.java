package com.shoonglogitics.orderservice.domain.order.domain.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.shoonglogitics.orderservice.domain.order.domain.entity.Order;
import com.shoonglogitics.orderservice.domain.common.dto.PageRequest;

public interface OrderRepository {
	Order save(Order order);

	Optional<Order> findById(UUID orderId);

	Page<Order> getOrdersByMaster(PageRequest pageRequest);

	Page<Order> getOrdersByUserId(Long userId, PageRequest pageRequest);

	List<Order> getAllOrders();

	void deleteAll();
}
