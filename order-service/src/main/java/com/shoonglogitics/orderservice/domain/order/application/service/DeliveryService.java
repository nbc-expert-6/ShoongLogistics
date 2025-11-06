package com.shoonglogitics.orderservice.domain.order.application.service;

import java.util.UUID;

public interface DeliveryService {
	void createDelivery(UUID orderId);
}
