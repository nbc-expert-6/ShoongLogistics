package com.shoonglogitics.orderservice.order.domain.vo;

public enum OrderStatus {
    PAYMENT_PENDING("결제 대기중"),
    PAYMENT_COMPLETED("결제 완료"),
    DELIVERY_PENDING("배송 대기중"),
    DELIVERING("배송중"),
    DELIVERY_COMPLETED("배송완료"),
    ORDER_CANCELLED("주문취소"),
    ORDER_FAILED("주문실패");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
