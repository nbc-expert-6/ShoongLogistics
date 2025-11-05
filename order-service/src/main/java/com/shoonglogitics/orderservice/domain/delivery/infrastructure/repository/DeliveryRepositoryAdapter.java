package com.shoonglogitics.orderservice.domain.delivery.infrastructure.repository;

import com.shoonglogitics.orderservice.domain.delivery.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeliveryRepositoryAdapter implements DeliveryRepository {
    private final JpaDeliveryRepository jpaDeliveryRepository;
}
