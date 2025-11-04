package com.shoonglogitics.orderservice.delivery.infrastructure.repository;

import com.shoonglogitics.orderservice.delivery.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DeliveryRepositoryAdapter implements DeliveryRepository {
    private final JpaDeliveryRepository jpaDeliveryRepository;
}
