package com.shoonglogitics.orderservice.domain.delivery.presentation;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoonglogitics.orderservice.domain.delivery.application.DeliveryService;
import com.shoonglogitics.orderservice.domain.delivery.application.command.CreateDeliveryCommand;
import com.shoonglogitics.orderservice.domain.delivery.application.dto.CreateDeliveryResult;
import com.shoonglogitics.orderservice.domain.delivery.application.dto.FindDeliveryResult;
import com.shoonglogitics.orderservice.domain.delivery.presentation.dto.CreateDeliveryRequest;
import com.shoonglogitics.orderservice.domain.delivery.presentation.dto.CreateDeliveryResponse;
import com.shoonglogitics.orderservice.domain.delivery.presentation.dto.FindDeliveryResponse;
import com.shoonglogitics.orderservice.global.common.exception.ApiResponse;
import com.shoonglogitics.orderservice.global.common.vo.AuthUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {

	private final DeliveryService deliveryService;

	@PostMapping
	public ResponseEntity<ApiResponse<CreateDeliveryResponse>> createDelivery(
		@RequestBody CreateDeliveryRequest request, @AuthenticationPrincipal AuthUser authUser
	) {
		CreateDeliveryResult result = deliveryService.createDelivery(
			CreateDeliveryCommand.from(request, authUser.getUserId(), authUser.getRole())
		);
		CreateDeliveryResponse response = CreateDeliveryResponse.from(result);
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response, "배송 정보가 생성되었습니다."));
	}

	//배송 정보 조회
	@GetMapping("/orders/{orderId}")
	public ResponseEntity<ApiResponse<FindDeliveryResponse>> getDelivery(
		@PathVariable("orderId") UUID orderId
	) {
		FindDeliveryResult result = deliveryService.getDelivery(orderId);
		FindDeliveryResponse response = FindDeliveryResponse.from(result);
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response));
	}

	//배송 경로 조회

	//배송 정보 수정

	//배송 경로 수정

	//배송 삭제
}
