package com.shoonglogitics.orderservice.delivery.infrastructure.config;

import com.shoonglogitics.orderservice.delivery.domain.repository.DeliveryRepository;
import com.shoonglogitics.orderservice.delivery.infrastructure.repository.DeliveryRepositoryAdapter;
import com.shoonglogitics.orderservice.delivery.infrastructure.repository.JpaDeliveryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {
    @Bean
    public DeliveryRepository deliveryRepository(
            JpaDeliveryRepository jpaDeliveryRepository
    ) {
        return new DeliveryRepositoryAdapter(jpaDeliveryRepository);
    }
}
