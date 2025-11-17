package com.shoonglogitics.orderservice.domain.product.application.service;

import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shoonglogitics.orderservice.domain.product.domain.entity.Stock;
import com.shoonglogitics.orderservice.domain.product.domain.repository.StockRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "Stock Service")
public class StockService {

	private final StockRepository stockRepository;
	private final ApplicationEventPublisher eventPublisher;

	@Transactional
	public void decreaseStock(UUID productId, Integer amount) {
		Stock stock = stockRepository.findByProductId(productId).orElseThrow(
			() -> new IllegalArgumentException("해당 상품의 재고 정보를 찾을 수 없습니다.")
		);
		stock.decreaseStock(amount);
	}
}
