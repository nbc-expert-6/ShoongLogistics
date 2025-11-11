package com.shoonglogitics.orderservice.domain.delivery.presentation;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shoonglogitics.orderservice.domain.delivery.application.DeliveryService;
import com.shoonglogitics.orderservice.domain.delivery.application.command.CreateDeliveryCommand;
import com.shoonglogitics.orderservice.domain.delivery.application.command.DeleteDeliveryCommand;
import com.shoonglogitics.orderservice.domain.delivery.application.command.UpdateDeliveryCommand;
import com.shoonglogitics.orderservice.domain.delivery.application.dto.CreateDeliveryResult;
import com.shoonglogitics.orderservice.domain.delivery.application.dto.FindDeliveryResult;
import com.shoonglogitics.orderservice.domain.delivery.application.dto.ListDeliveryRouteResult;
import com.shoonglogitics.orderservice.domain.delivery.application.query.ListDeliveryRouteQuery;
import com.shoonglogitics.orderservice.domain.delivery.presentation.dto.CreateDeliveryRequest;
import com.shoonglogitics.orderservice.domain.delivery.presentation.dto.CreateDeliveryResponse;
import com.shoonglogitics.orderservice.domain.delivery.presentation.dto.DeleteDeliveryResponse;
import com.shoonglogitics.orderservice.domain.delivery.presentation.dto.FindDeliveryResponse;
import com.shoonglogitics.orderservice.domain.delivery.presentation.dto.ListDeliveryRouteResponse;
import com.shoonglogitics.orderservice.domain.delivery.presentation.dto.ProcessHubShippingCommand;
import com.shoonglogitics.orderservice.domain.delivery.presentation.dto.ProcessHubShippingRequest;
import com.shoonglogitics.orderservice.domain.delivery.presentation.dto.ProcessHubShippingResponse;
import com.shoonglogitics.orderservice.domain.delivery.presentation.dto.UpdateDeliveryRequest;
import com.shoonglogitics.orderservice.domain.delivery.presentation.dto.UpdateDeliveryResponse;
import com.shoonglogitics.orderservice.global.common.dto.PageRequest;
import com.shoonglogitics.orderservice.global.common.dto.PageResponse;
import com.shoonglogitics.orderservice.global.common.exception.ApiResponse;
import com.shoonglogitics.orderservice.global.common.vo.AuthUser;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {

	private final DeliveryService deliveryService;

	@PostMapping
	@PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER', 'SHIPPER', 'COMPANY_MANAGER')")
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
	@PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER', 'SHIPPER', 'COMPANY_MANAGER')")
	public ResponseEntity<ApiResponse<FindDeliveryResponse>> getDelivery(
		@PathVariable("orderId") UUID orderId
	) {
		FindDeliveryResult result = deliveryService.getDelivery(orderId);
		FindDeliveryResponse response = FindDeliveryResponse.from(result);
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response));
	}

	//배송 경로 조회
	@GetMapping("/{deliveryId}")
	@PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER', 'SHIPPER', 'COMPANY_MANAGER')")
	public ResponseEntity<ApiResponse<PageResponse<ListDeliveryRouteResponse>>> getDeliveries(
		@PathVariable("deliveryId") UUID deliveryId,
		@ModelAttribute PageRequest pageRequest
	) {
		ListDeliveryRouteQuery query = ListDeliveryRouteQuery.from(deliveryId, pageRequest);
		Page<ListDeliveryRouteResult> result = deliveryService.getDeliveryRoutes(query);
		Page<ListDeliveryRouteResponse> response = result.map(ListDeliveryRouteResponse::from);
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(PageResponse.of(response)));
	}

	//배송 정보 수정
	@PutMapping("/{deliveryId}")
	@PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER')")
	public ResponseEntity<ApiResponse<UpdateDeliveryResponse>> updateDelivery(
		@PathVariable("deliveryId") UUID deliveryId,
		@RequestBody UpdateDeliveryRequest request,
		@AuthenticationPrincipal AuthUser authUser
	) {
		UUID updatedDeliveryId = deliveryService.updateDelivery(
			UpdateDeliveryCommand.from(deliveryId, request, authUser));
		UpdateDeliveryResponse response = UpdateDeliveryResponse.from(updatedDeliveryId);
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(response));
	}

	//배송 삭제
	@DeleteMapping("/{deliveryId}")
	@PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER')")
	public ResponseEntity<ApiResponse<DeleteDeliveryResponse>> deleteDelivery(
		@PathVariable("deliveryId") UUID deliveryId,
		@AuthenticationPrincipal AuthUser authUser
	) {
		UUID deletedDeliveryId = deliveryService.deleteDelivery(DeleteDeliveryCommand.from(
			deliveryId, authUser.getUserId(), authUser.getRole()
		));
		return ResponseEntity.status(HttpStatus.OK)
			.body(ApiResponse.success(DeleteDeliveryResponse.from(deletedDeliveryId), "배송 정보가 삭제되었습니다."));
	}

	//허브 출발 & 도착 처리
	@PatchMapping("/{deliveryId}/delivery-routes/{deliveryRouteId}/shipping")
	public ResponseEntity<ApiResponse<ProcessHubShippingResponse>> processHubShipping(
		@AuthenticationPrincipal AuthUser authUser,
		@PathVariable("deliveryId") UUID deliveryId,
		@PathVariable("deliveryRouteId") UUID deliveryRouteId,
		@RequestBody ProcessHubShippingRequest request
	) {
		UUID updatedDeliveryRouteId = deliveryService.processHubShipping(
			ProcessHubShippingCommand.from(
				deliveryId,
				deliveryRouteId,
				request.isDeparture(),
				request.distance(),
				request.duration(),
				authUser
			)
		);
		return ResponseEntity.status(HttpStatus.OK)
			.body(ApiResponse.success(ProcessHubShippingResponse.from(updatedDeliveryRouteId)));
	}

	//배송 출발 & 도착 처리
}
