package com.shoonglogitics.orderservice.domain.order.application.service;

import java.util.UUID;

public interface DeliveryClient {
	void createDelivery(UUID order);

}
