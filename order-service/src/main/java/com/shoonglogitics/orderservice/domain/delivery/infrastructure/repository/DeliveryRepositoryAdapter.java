package com.shoonglogitics.orderservice.domain.delivery.infrastructure.repository;

import org.springframework.stereotype.Repository;

import com.shoonglogitics.orderservice.domain.delivery.domain.entity.Delivery;
import com.shoonglogitics.orderservice.domain.delivery.domain.repository.DeliveryRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class DeliveryRepositoryAdapter implements DeliveryRepository {
	private final JpaDeliveryRepository jpaDeliveryRepository;

	@Override
	public Delivery save(Delivery delivery) {
		return jpaDeliveryRepository.save(delivery);
	}
}
