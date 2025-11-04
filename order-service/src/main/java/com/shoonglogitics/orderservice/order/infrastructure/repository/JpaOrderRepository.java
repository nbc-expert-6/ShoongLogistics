package com.shoonglogitics.orderservice.order.infrastructure.repository;


import com.shoonglogitics.orderservice.order.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface JpaOrderRepository extends JpaRepository<Order, UUID> {
}
