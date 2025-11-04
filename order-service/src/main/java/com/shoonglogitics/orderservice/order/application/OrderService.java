package com.shoonglogitics.orderservice.order.application;

import com.shoonglogitics.orderservice.order.domain.repository.OrderRepository;
import com.shoonglogitics.orderservice.order.domain.service.OrderDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDomainService orderDomainService;

}
