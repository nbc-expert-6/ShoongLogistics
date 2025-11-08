package com.shoonglogitics.orderservice.domain.delivery.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.shoonglogitics.orderservice.domain.delivery.domain.entity.Delivery;
import com.shoonglogitics.orderservice.domain.delivery.domain.entity.DeliveryRoute;
import com.shoonglogitics.orderservice.global.common.dto.PageRequest;

public interface DeliveryRepository {
	Delivery save(Delivery delivery);

	Optional<Delivery> findById(UUID deliveryId);

	Optional<Delivery> findByOrderId(UUID orderId);

	Page<DeliveryRoute> getDeliveryRoutes(UUID uuid, PageRequest pageRequest);
}
