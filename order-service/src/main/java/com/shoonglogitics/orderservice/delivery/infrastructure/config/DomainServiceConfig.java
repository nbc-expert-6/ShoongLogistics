package com.shoonglogitics.orderservice.delivery.infrastructure.config;

import com.shoonglogitics.orderservice.delivery.domain.service.DeliveryDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("DeliveryDomainServiceConfig")
public class DomainServiceConfig {

    @Bean
    public DeliveryDomainService domainService() {
        return new DeliveryDomainService();
    }
}
