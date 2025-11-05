package com.shoonglogitics.orderservice.domain.delivery.infrastructure.repository;

import com.shoonglogitics.orderservice.domain.delivery.domain.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaDeliveryRepository extends JpaRepository<Delivery, UUID> {
}
