package com.shoonglogitics.orderservice.domain.order.infrastructure.external.client.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.shoonglogitics.orderservice.domain.order.application.command.CreateOrderItemCommand;
import com.shoonglogitics.orderservice.domain.order.application.dto.StockInfo;
import com.shoonglogitics.orderservice.domain.order.application.service.CompanyClient;
import com.shoonglogitics.orderservice.domain.order.infrastructure.external.client.feign.CompanyFeignClient;
import com.shoonglogitics.orderservice.domain.order.infrastructure.external.dto.FeginStockDecreaseRequest;
import com.shoonglogitics.orderservice.domain.order.infrastructure.external.dto.FeignStockInfoResponse;
import com.shoonglogitics.orderservice.domain.order.infrastructure.external.mapper.CompanyMapper;
import com.shoonglogitics.orderservice.global.common.exception.ApiResponse;
import com.shoonglogitics.orderservice.global.common.vo.UserRoleType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component("orderCompanyClientImpl")
@RequiredArgsConstructor
@Slf4j
public class CompanyClientImpl implements CompanyClient {

	private final CompanyFeignClient companyFeignClient;

	@Override
	public void validateItems(List<CreateOrderItemCommand> orderItems) {
		//실제 구현

	}

	@Override
	public void decreaseStock(UUID stockId, Integer quantity, Long userId, UserRoleType role) {
		//재고 감소 요청
		ResponseEntity<ApiResponse> response = companyFeignClient.decreaseStock(stockId,
			FeginStockDecreaseRequest.from(quantity, "주문으로 인한 감소"),
			userId, role);
		log.info("재고 감소 요청 호출");

	}

	@Override
	public StockInfo getStockInfo(UUID productId, Long userId, UserRoleType role) {
		ResponseEntity<ApiResponse<FeignStockInfoResponse>> response = companyFeignClient.getStockInfo(productId,
			userId, role);
		return CompanyMapper.toStockInfo(response.getBody().data());
	}
}
