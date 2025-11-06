package com.shoonglogitics.orderservice.domain.order.infrastructure.external;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.shoonglogitics.orderservice.domain.order.application.service.DeliveryClient;
import com.shoonglogitics.orderservice.domain.order.application.service.DeliveryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

	private final DeliveryClient deliveryClient;

	@Override
	public void createDelivery(UUID orderId) {

	}
}
