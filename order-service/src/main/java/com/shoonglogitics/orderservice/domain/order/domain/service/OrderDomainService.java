package com.shoonglogitics.orderservice.domain.order.domain.service;

import java.util.List;

import com.shoonglogitics.orderservice.domain.order.domain.entity.OrderItem;
import com.shoonglogitics.orderservice.domain.order.domain.vo.Money;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderDomainService {
	public void validateOrder(List<OrderItem> orderItems, Money totalPrice) {
		log.info("주문 검증 시작");

		//주문 항목 수 검증
		if (orderItems == null || orderItems.isEmpty()) {
			throw new IllegalArgumentException("주문 항목은 최소 1개 이상이어야 합니다.");
		}

		//각 상품 가격 * 수량의 총합 계산
		Money calculatedTotal = orderItems.stream()
			.map(OrderItem::calculateTotalPrice)
			.reduce(Money.zero(), Money::add);

		//총 주문 금액 검증
		if (!calculatedTotal.equals(totalPrice)) {
			log.warn("주문 검증 실패. 주문 금액 합계: {}, 요청 총액: {} ", calculatedTotal.getAmount(), totalPrice.getAmount());
			throw new IllegalArgumentException(
				String.format("주문 금액 합계(%s)와 요청 총액(%s)이 일치하지 않습니다.", calculatedTotal.getAmount(),
					totalPrice.getAmount())
			);
		}

		log.info("주문 검증 통과. 요청 총액: {}", totalPrice.getAmount());

	}
}
