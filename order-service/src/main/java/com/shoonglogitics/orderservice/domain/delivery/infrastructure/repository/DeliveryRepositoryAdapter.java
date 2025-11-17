package com.shoonglogitics.orderservice.domain.delivery.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import com.shoonglogitics.orderservice.domain.delivery.domain.entity.Delivery;
import com.shoonglogitics.orderservice.domain.delivery.domain.entity.DeliveryRoute;
import com.shoonglogitics.orderservice.domain.delivery.domain.repository.DeliveryRepository;
import com.shoonglogitics.orderservice.domain.common.dto.PageRequest;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DeliveryRepositoryAdapter implements DeliveryRepository {
	private final JpaDeliveryRepository jpaDeliveryRepository;

	@Override
	public Delivery save(Delivery delivery) {
		return jpaDeliveryRepository.save(delivery);
	}

	@Override
	public Optional<Delivery> findById(UUID deliveryId) {
		return jpaDeliveryRepository.findById(deliveryId);
	}

	@Override
	public Optional<Delivery> findByOrderId(UUID orderId) {
		return jpaDeliveryRepository.findByOrderId(orderId);
	}

	@Override
	public Page<DeliveryRoute> getDeliveryRoutes(UUID uuid, PageRequest pageRequest) {
		return jpaDeliveryRepository.findAllDeliveryRouteById(uuid, pageRequest.toNativePageable());
	}
}
