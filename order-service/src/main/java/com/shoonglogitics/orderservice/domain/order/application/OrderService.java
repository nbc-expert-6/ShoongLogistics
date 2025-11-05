package com.shoonglogitics.orderservice.domain.order.application;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.shoonglogitics.orderservice.domain.order.application.command.CreateOrderCommand;
import com.shoonglogitics.orderservice.domain.order.domain.repository.OrderRepository;
import com.shoonglogitics.orderservice.domain.order.domain.service.OrderDomainService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j(topic = "order-service")
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final OrderDomainService orderDomainService;

	public UUID createOrder(CreateOrderCommand command) {
		log.info("Create order: {}", command);
	}
}
