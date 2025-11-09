package com.shoonglogitics.orderservice.domain.order.presentation;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoonglogitics.orderservice.domain.order.application.OrderService;
import com.shoonglogitics.orderservice.domain.order.application.command.CreateOrderCommand;
import com.shoonglogitics.orderservice.domain.order.application.command.DeleteOrderCommand;
import com.shoonglogitics.orderservice.domain.order.application.dto.FindOrderResult;
import com.shoonglogitics.orderservice.domain.order.application.dto.ListOrderResult;
import com.shoonglogitics.orderservice.domain.order.application.dto.UpdateOrderCommand;
import com.shoonglogitics.orderservice.domain.order.application.query.ListOrderQuery;
import com.shoonglogitics.orderservice.domain.order.presentation.dto.CreateOrderRequest;
import com.shoonglogitics.orderservice.domain.order.presentation.dto.CreateOrderResponse;
import com.shoonglogitics.orderservice.domain.order.presentation.dto.DeleteOrderResponse;
import com.shoonglogitics.orderservice.domain.order.presentation.dto.FindOrderResponse;
import com.shoonglogitics.orderservice.domain.order.presentation.dto.ListOrderResponse;
import com.shoonglogitics.orderservice.domain.order.presentation.dto.UpdateOrderRequest;
import com.shoonglogitics.orderservice.domain.order.presentation.dto.UpdateOrderResponse;
import com.shoonglogitics.orderservice.global.common.dto.PageRequest;
import com.shoonglogitics.orderservice.global.common.dto.PageResponse;
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
	@PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER', 'SHIPPER', 'COMPANY_MANAGER')")
	public ResponseEntity<ApiResponse<CreateOrderResponse>> createOrder(
		@Valid @RequestBody CreateOrderRequest request,
		@AuthenticationPrincipal AuthUser authUser) {
		UUID orderId = orderService.createOrder(
			CreateOrderCommand.from(request, authUser.getUserId(), authUser.getRole()));
		CreateOrderResponse response = new CreateOrderResponse(
			orderId,
			"주문이 생성에 성공했습니다."
		);
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response, "요청 성공"));
	}

	@GetMapping("/{orderId}")
	@PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER', 'SHIPPER', 'COMPANY_MANAGER')")
	public ResponseEntity<ApiResponse<FindOrderResponse>> getOrder(
		@PathVariable UUID orderId
	) {
		FindOrderResult result = orderService.getOrder(orderId);
		FindOrderResponse response = FindOrderResponse.from(result);
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response, "요청 성공"));
	}

	//주문 목록 조회
	@GetMapping
	@PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER', 'SHIPPER', 'COMPANY_MANAGER')")
	public ResponseEntity<ApiResponse<PageResponse<ListOrderResponse>>> listOrders(
		@AuthenticationPrincipal AuthUser authUser,
		@ModelAttribute PageRequest pageRequest
	) {
		Page<ListOrderResult> result = orderService.listOrders(
			ListOrderQuery.from(authUser.getUserId(), authUser.getRole(), pageRequest));
		Page<ListOrderResponse> response = result.map(ListOrderResponse::from);
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(PageResponse.of(response)));
	}

	//주문 수정
	@PutMapping("/{orderId}")
	@PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER')")
	public ResponseEntity<ApiResponse<UpdateOrderResponse>> updateOrder(
		@PathVariable("orderId") UUID orderId,
		@AuthenticationPrincipal AuthUser authUser,
		@RequestBody UpdateOrderRequest request
	) {
		UUID updatedOrderId = orderService.updateOrder(UpdateOrderCommand.from(
			request.request(), request.deliveryRequest(), authUser, orderId
		));
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(UpdateOrderResponse.from(updatedOrderId)));
	}

	//주문 삭제
	@DeleteMapping("/{orderId}")
	@PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER')")
	public ResponseEntity<ApiResponse<DeleteOrderResponse>> deleteOrder(
		@AuthenticationPrincipal AuthUser authUser,
		@PathVariable("orderId") UUID orderId
	) {
		UUID deletedOrderId = orderService.cancleOrder(DeleteOrderCommand.from(
			orderId, authUser
		));
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(DeleteOrderResponse.from(deletedOrderId)));
	}
}
