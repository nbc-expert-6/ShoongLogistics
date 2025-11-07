package com.shoonglogitics.notificationservice.presentation.dto;

import java.util.UUID;

import com.shoonglogitics.notificationservice.application.command.CreateAdviceCommand;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AIDeliveryAdviceRequest {

	private UUID orderId;           // 주문 번호
	private String productInfo;       // 상품 및 수량 정보
	private String deliveryRequest;   // 요청 사항 (납기일 등)
	private String origin;            // 발송지
	private String destination;       // 도착지
	private String customerName;      // 주문자 이름
	private String customerEmail;     // 주문자 이메일
	private String managerName;       // 담당자 이름
	private String managerEmail;      // 담당자 이메일

	public CreateAdviceCommand toCommand() {
		return CreateAdviceCommand.builder()
			.orderId(this.orderId)
			.productInfo(this.productInfo)
			.deliveryRequest(this.deliveryRequest)
			.origin(this.origin)
			.destination(this.destination)
			.customerName(this.customerName)
			.customerEmail(this.customerEmail)
			.managerName(this.managerName)
			.managerEmail(this.managerEmail)
			.build();
	}
}
