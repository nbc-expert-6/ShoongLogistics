package com.shoonglogitics.orderservice.domain.order.infrastructure.config;

import com.shoonglogitics.orderservice.domain.order.domain.repository.OrderRepository;
import com.shoonglogitics.orderservice.domain.order.infrastructure.repository.JpaOrderRepository;
import com.shoonglogitics.orderservice.domain.order.infrastructure.repository.OrderRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("orderRepositoryConfig")
public class RepositoryConfig {

    @Bean
    public OrderRepository orderRepository(JpaOrderRepository jpaOrderRepository) {
        return new OrderRepositoryAdapter(jpaOrderRepository);
    }
}
