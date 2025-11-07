package com.shoonglogitics.orderservice.domain.order.presentation;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoonglogitics.orderservice.domain.order.application.OrderService;
import com.shoonglogitics.orderservice.domain.order.application.command.CreateOrderCommand;
import com.shoonglogitics.orderservice.domain.order.presentation.dto.CreateOrderRequest;
import com.shoonglogitics.orderservice.domain.order.presentation.dto.CreateOrderResponse;
import com.shoonglogitics.orderservice.global.common.exception.ApiResponse;
import com.shoonglogitics.orderservice.global.common.vo.AuthUser;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/orders")
public class OrderController {

	private final OrderService orderService;

	@PostMapping
	public ResponseEntity<ApiResponse<CreateOrderResponse>> createOrder(
		@Valid @RequestBody CreateOrderRequest request,
		@AuthenticationPrincipal AuthUser authUser) {
		UUID orderId = orderService.createOrder(
			CreateOrderCommand.from(request, authUser.getUserId(), authUser.getRole()));
		CreateOrderResponse response = new CreateOrderResponse(
			orderId,
			"주문이 생성에 성공했습니다."
		);
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
	}
}
