package com.shoonglogitics.orderservice.order.infrastructure.config;

import com.shoonglogitics.orderservice.order.domain.repository.OrderRepository;
import com.shoonglogitics.orderservice.order.infrastructure.repository.JpaOrderRepository;
import com.shoonglogitics.orderservice.order.infrastructure.repository.OrderRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("orderRepositoryConfig")
public class RepositoryConfig {

    @Bean
    public OrderRepository orderRepository(JpaOrderRepository jpaOrderRepository) {
        return new OrderRepositoryAdapter(jpaOrderRepository);
    }
}
