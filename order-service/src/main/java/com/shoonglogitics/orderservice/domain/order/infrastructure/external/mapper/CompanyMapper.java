package com.shoonglogitics.orderservice.domain.order.infrastructure.external.mapper;

import com.shoonglogitics.orderservice.domain.order.application.dto.StockInfo;
import com.shoonglogitics.orderservice.domain.order.infrastructure.external.dto.FeignStockInfoResponse;

public final class CompanyMapper {

	public static StockInfo toStockInfo(FeignStockInfoResponse response) {
		return StockInfo.from(response.id(), response.productId(), response.amount());
	}
}
