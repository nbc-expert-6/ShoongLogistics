package com.shoonglogitics.orderservice.domain.order.infrastructure.external.mapper;

import com.shoonglogitics.orderservice.domain.order.application.dto.OrderItemInfo;
import com.shoonglogitics.orderservice.domain.order.application.dto.StockInfo;
import com.shoonglogitics.orderservice.domain.order.infrastructure.external.dto.FeignProductResponse;
import com.shoonglogitics.orderservice.domain.order.infrastructure.external.dto.FeignStockInfoResponse;

public final class CompanyMapper {

	public static StockInfo toStockInfo(FeignStockInfoResponse response) {
		return StockInfo.from(response.id(), response.productId(), response.amount());
	}

	public static OrderItemInfo toOrderItemInfo(FeignProductResponse response) {
		return OrderItemInfo.from(response.id(), response.price());
	}
}
