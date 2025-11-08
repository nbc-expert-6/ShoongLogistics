package com.shoonglogitics.orderservice.domain.delivery.domain.repository;

import com.shoonglogitics.orderservice.domain.delivery.domain.entity.Delivery;

public interface DeliveryRepository {
	Delivery save(Delivery delivery);
}
