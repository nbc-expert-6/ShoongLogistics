package com.shoonglogitics.orderservice.domain.order.infrastructure.external;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.shoonglogitics.orderservice.domain.order.application.service.DeliveryClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeliveryClientImpl implements DeliveryClient {
	@Override
	public void createDelivery(UUID order) {

	}
}
