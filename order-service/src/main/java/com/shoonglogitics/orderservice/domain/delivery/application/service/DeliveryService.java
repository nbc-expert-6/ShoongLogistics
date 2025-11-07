package com.shoonglogitics.orderservice.domain.delivery.application.service;

import org.springframework.stereotype.Service;

import com.shoonglogitics.orderservice.domain.delivery.application.command.CreateDeliveryCommand;
import com.shoonglogitics.orderservice.domain.delivery.application.dto.CreateDeliveryResult;
import com.shoonglogitics.orderservice.domain.delivery.domain.repository.DeliveryRepository;
import com.shoonglogitics.orderservice.domain.delivery.domain.service.DeliveryDomainService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryService {
	private final DeliveryRepository deliveryRepository;
	private final DeliveryDomainService domainService;

	public CreateDeliveryResult createDelivery(CreateDeliveryCommand from) {

		return null;
	}
}
