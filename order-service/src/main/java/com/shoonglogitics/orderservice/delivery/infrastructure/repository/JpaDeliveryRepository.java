package com.shoonglogitics.orderservice.delivery.infrastructure.repository;

import com.shoonglogitics.orderservice.delivery.domain.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaDeliveryRepository extends JpaRepository<Delivery, UUID> {
}
