package com.shoonglogitics.orderservice.domain.order.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ProductInfo {

    private UUID productId;
    private Integer price;

    public static ProductInfo of(UUID productId, Integer price) {
        if (price <= 0) {
            throw new IllegalArgumentException("상품 가격은 0이하일 수 없습니다.");
        }
        ProductInfo productInfo = new ProductInfo();
        productInfo.productId = productId;
        productInfo.price = price;
        return productInfo;
    }
}
