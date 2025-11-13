package com.shoonglogitics.orderservice.domain.delivery.infrastructure.config;

import com.shoonglogitics.orderservice.domain.delivery.domain.repository.DeliveryRepository;
import com.shoonglogitics.orderservice.domain.delivery.infrastructure.repository.DeliveryRepositoryAdapter;
import com.shoonglogitics.orderservice.domain.delivery.infrastructure.repository.JpaDeliveryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("deliveryRepositoryConfig")
public class RepositoryConfig {
    @Bean
    public DeliveryRepository deliveryRepository(
            JpaDeliveryRepository jpaDeliveryRepository
    ) {
        return new DeliveryRepositoryAdapter(jpaDeliveryRepository);
    }
}
