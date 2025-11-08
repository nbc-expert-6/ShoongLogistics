package com.shoonglogitics.orderservice.domain.delivery.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoonglogitics.orderservice.domain.delivery.domain.entity.Delivery;

public interface JpaDeliveryRepository extends JpaRepository<Delivery, UUID> {
	Optional<Delivery> findByOrderId(UUID orderId);
}
