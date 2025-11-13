package com.shoonglogitics.companyservice.infrastructure.external;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.shoonglogitics.companyservice.application.service.StockClient;
import com.shoonglogitics.companyservice.domain.common.vo.UserRoleType;
import com.shoonglogitics.companyservice.infrastructure.external.dto.CreateStockFeignClientRequest;
import com.shoonglogitics.companyservice.infrastructure.external.dto.CreateStockFeignClientResponse;
import com.shoonglogitics.companyservice.presentation.common.dto.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockClientAdapter implements StockClient {
	private final StockFeignClient stockFeignClient;

	@Override
	public void createStock(UUID productId, Long userId) {
		CreateStockFeignClientRequest request = new CreateStockFeignClientRequest(productId, 0);
		ApiResponse<CreateStockFeignClientResponse> response = stockFeignClient.createStock(request, userId, UserRoleType.MASTER);
		if (!response.success()) {
			log.warn("상품 재고 생성 실패 - message: {}", response.message());
			throw new IllegalArgumentException(response.message());
		}
	}

	@Override
	public void deleteStock(UUID productId, Long userId) {
		ApiResponse<Void> response = stockFeignClient.deleteStock(productId, userId, UserRoleType.MASTER);

		if (!response.success()) {
			log.warn("상품 재고 삭제 실패 - message: {}", response.message());
			throw new IllegalArgumentException(response.message());
		}
	}
}
