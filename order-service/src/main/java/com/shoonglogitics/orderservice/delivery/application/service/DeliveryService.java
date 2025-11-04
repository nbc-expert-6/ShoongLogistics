package com.shoonglogitics.orderservice.delivery.application.service;

import com.shoonglogitics.orderservice.delivery.domain.repository.DeliveryRepository;
import com.shoonglogitics.orderservice.delivery.domain.service.DeliveryDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final DeliveryDomainService domainService;
}
