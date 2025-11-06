package com.shoonglogitics.orderservice.domain.order.domain.vo;

import java.util.UUID;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ProductInfo {

	private UUID productId;
	private Money price;

	public static ProductInfo of(UUID productId, Money price) {
		if (price.isNegative()) {
			throw new IllegalArgumentException("상품 가격은 0보다 작을 수 없습니다.");
		}
		ProductInfo productInfo = new ProductInfo();
		productInfo.productId = productId;
		productInfo.price = price;
		return productInfo;
	}
}
