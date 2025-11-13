package com.shoonglogitics.notificationservice.application.command;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateAdviceCommand {

	private UUID orderId;
	private String productInfo;

	private String deliveryRequest;   // 납기 요청사항
	private String origin;            // 발송지
	private String destination;       // 도착지

	private String customerName;
	private String customerEmail;
	private String managerName;
	private String managerEmail;
}
