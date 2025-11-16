package com.shoonglogitics.orderservice.domain.order.domain.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.shoonglogitics.orderservice.domain.order.domain.entity.Order;
import com.shoonglogitics.orderservice.domain.order.domain.vo.ProductInfo;

import lombok.Getter;

@Getter
public class OrderCreatedEvent {

	private final UUID orderId;
	private final UUID productId;
	private final BigDecimal totalPrice;
	private final BigDecimal price;
	private final Integer quantity;
	private final LocalDateTime occurredAt;

	public OrderCreatedEvent(Order order) {
		ProductInfo productInfo = order.getOrderItems().get(0).getProductInfo();
		this.orderId = order.getId();
		this.productId = productInfo.getProductId();
		this.totalPrice = order.getTotalPrice().getAmount();
		this.price = productInfo.getPrice().getAmount();
		this.quantity = order.getOrderItems().get(0).getQuantity().getValue();
		this.occurredAt = LocalDateTime.now();
	}
}
