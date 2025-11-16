package com.shoonglogitics.orderservice.domain.common.vo;

import lombok.Getter;

@Getter
public enum ShipperType {
	HUB_SHIPPER, // 허브 배송 담당자
	COMPANY_SHIPPER; // 업체 배송 담당자
}
