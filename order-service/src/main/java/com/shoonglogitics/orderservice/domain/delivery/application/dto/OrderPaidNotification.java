// package com.shoonglogitics.orderservice.domain.delivery.application.dto;
//
// import java.util.UUID;
// import java.util.stream.Collectors;
//
// import com.shoonglogitics.orderservice.domain.order.domain.entity.Order;
//
// public record OrderPaidNotification(
// 	UUID orderId,          // 주문 번호
// 	String productInfo,    // 상품 및 수량 정보
// 	String deliveryRequest,// 요청 사항 (납기일 등)
// 	String origin,         // 발송지
// 	String destination,    // 도착지
// 	String customerName,   // 주문자 이름
// 	String customerEmail,  // 주문자 이메일
// 	String managerName,    // 담당자 이름
// 	String managerEmail    // 담당자 이메일
// ) {
// 	public static OrderPaidNotification from(Order order) {
// 		String productInfo = order.getOrderItems().stream()
// 			.map(item -> item.getProductInfo().getProductId() + " x" + item.getQuantity().getValue())
// 			.collect(Collectors.joining(", "));
//
// 		String fullAddress = String.format(
// 			"%s %s (%s)",
// 			order.getAddress().getAddress(),
// 			order.getAddress().getAddressDetail(),
// 			order.getAddress().getZipCode());
//
// 		return new OrderPaidNotification(
// 			order.getId(),
// 			productInfo,
// 			fullAddress
//
// 		);
//
//
// 		)
// 	}
// }