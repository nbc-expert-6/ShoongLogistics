package com.shoonglogitics.orderservice.domain.order.infrastructure.repository;


import com.shoonglogitics.orderservice.domain.order.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaOrderRepository extends JpaRepository<Order, UUID> {
}
