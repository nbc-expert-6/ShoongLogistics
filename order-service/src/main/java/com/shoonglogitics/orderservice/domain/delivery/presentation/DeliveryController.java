package com.shoonglogitics.orderservice.domain.delivery.presentation;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoonglogitics.orderservice.domain.delivery.application.command.CreateDeliveryCommand;
import com.shoonglogitics.orderservice.domain.delivery.application.dto.CreateDeliveryResult;
import com.shoonglogitics.orderservice.domain.delivery.application.service.DeliveryService;
import com.shoonglogitics.orderservice.domain.delivery.presentation.dto.CreateDeliveryRequest;
import com.shoonglogitics.orderservice.domain.delivery.presentation.dto.CreateDeliveryResponse;
import com.shoonglogitics.orderservice.global.common.exception.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/delivery")
public class DeliveryController {

	private final DeliveryService deliveryService;

	@PostMapping
	public ResponseEntity<ApiResponse<CreateDeliveryResponse>> createDelivery(
		@RequestBody CreateDeliveryRequest request
	) {
		CreateDeliveryResult result = deliveryService.createDelivery(CreateDeliveryCommand.from(request));
		CreateDeliveryResponse response = CreateDeliveryResponse.from(result);
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response, "요청 성공"));
	}
}
