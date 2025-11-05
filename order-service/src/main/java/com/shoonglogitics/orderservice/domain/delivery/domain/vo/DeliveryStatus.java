package com.shoonglogitics.orderservice.domain.delivery.domain.vo;

import lombok.Getter;

@Getter
public enum DeliveryStatus {
    HUB_WAITING("허브 대기중"),
    HUB_TRANSIT("허브 이동중"),
    HUB_ARRIVED("목적지 허브 도착"),
    IN_DELIVERY("배송중"),
    TO_VENDOR("업체이동중"),
    DELIVERED("배송완료");

    private final String description;

    DeliveryStatus(String description) {
        this.description = description;
    }

}
