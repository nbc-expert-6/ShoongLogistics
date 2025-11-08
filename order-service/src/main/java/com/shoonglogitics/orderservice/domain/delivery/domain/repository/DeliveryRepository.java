package com.shoonglogitics.orderservice.domain.delivery.domain.repository;

import java.util.Optional;
import java.util.UUID;

import com.shoonglogitics.orderservice.domain.delivery.domain.entity.Delivery;

public interface DeliveryRepository {
	Delivery save(Delivery delivery);

	Optional<Delivery> findById(UUID deliveryId);

	Optional<Delivery> findByOrderId(UUID orderId);
}
