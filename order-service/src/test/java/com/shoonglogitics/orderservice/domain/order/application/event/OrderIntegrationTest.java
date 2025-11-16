package com.shoonglogitics.orderservice.domain.order.application.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.shoonglogitics.orderservice.domain.order.application.OrderService;
import com.shoonglogitics.orderservice.domain.order.domain.repository.OrderRepository;
import com.shoonglogitics.orderservice.domain.payment.domain.repository.PaymentRepository;
import com.shoonglogitics.orderservice.domain.product.domain.repository.StockRepository;

@SpringBootTest
@ActiveProfiles("test")
class OrderIntegrationTest {

	@Autowired
	private OrderService orderService;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private PaymentRepository paymentRepository;

	@Autowired
	private StockRepository stockRepository;

}