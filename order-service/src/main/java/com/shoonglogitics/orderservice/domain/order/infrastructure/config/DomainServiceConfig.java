package com.shoonglogitics.orderservice.domain.order.infrastructure.config;

import com.shoonglogitics.orderservice.domain.order.domain.service.OrderDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("orderDomainServiceConfig")
public class DomainServiceConfig {

    @Bean
    public OrderDomainService orderDomainService() {
        return new OrderDomainService();
    }
}
