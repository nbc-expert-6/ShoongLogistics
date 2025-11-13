package com.shoonglogitics.orderservice.domain.delivery.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shoonglogitics.orderservice.domain.delivery.domain.entity.Delivery;
import com.shoonglogitics.orderservice.domain.delivery.domain.entity.DeliveryRoute;

public interface JpaDeliveryRepository extends JpaRepository<Delivery, UUID> {
	Optional<Delivery> findByOrderId(UUID orderId);

	@Query(
		value = "SELECT * FROM p_delivery_route WHERE delivery_id = :deliveryId AND deleted_at IS NULL",
		countQuery = "SELECT COUNT(*) FROM delivery_route WHERE delivery_id = :deliveryId AND deleted_at IS NULL",
		nativeQuery = true
	)
	Page<DeliveryRoute> findAllDeliveryRouteById(UUID deliveryId, Pageable pageable);
}
