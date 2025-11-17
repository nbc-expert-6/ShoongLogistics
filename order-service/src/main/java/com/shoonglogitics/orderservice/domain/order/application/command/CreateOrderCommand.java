package com.shoonglogitics.orderservice.domain.order.application.command;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.shoonglogitics.orderservice.domain.order.presentation.dto.CreateOrderRequest;
import com.shoonglogitics.orderservice.domain.common.vo.UserRoleType;

import lombok.Builder;

@Builder
public record CreateOrderCommand(
	Long userId,
	UserRoleType role,
	UUID receiverCompanyId,
	String receiverCompanyName,
	UUID supplierCompanyId,
	String supplierCompanyName,
	String request,
	String deliveryRequest,
	String address,
	String addressDetail,
	String zipCode,
	Double latitude,
	Double longitude,
	BigDecimal totalPrice,
	List<CreateOrderItemCommand> orderItems
) {
	public static CreateOrderCommand from(CreateOrderRequest request, Long userId, UserRoleType role) {
		List<CreateOrderItemCommand> items = request.orderItems().stream()
			.map(i -> new CreateOrderItemCommand(i.productId(), i.price(), i.quantity()))
			.toList();

		return new CreateOrderCommand(
			userId,
			role,
			request.receiverCompanyId(),
			request.receiverCompanyName(),
			request.supplierCompanyId(),
			request.supplierCompanyName(),
			request.request(),
			request.deliveryRequest(),
			request.address(),
			request.addressDetail(),
			request.zipCode(),
			request.latitude(),
			request.longitude(),
			request.totalPrice(),
			items
		);
	}

}
