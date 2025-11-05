package com.shoonglogitics.orderservice.domain.order.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoonglogitics.orderservice.domain.order.domain.entity.Order;

public interface JpaOrderRepository extends JpaRepository<Order, UUID> {

}
