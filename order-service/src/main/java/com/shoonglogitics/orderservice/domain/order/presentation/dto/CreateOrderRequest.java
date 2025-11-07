package com.shoonglogitics.orderservice.domain.order.presentation.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateOrderRequest(
	@NotNull(message = "수령업체 ID는 필수입니다.")
	UUID receiverCompanyId,

	@NotBlank(message = "수령업체 이름은 필수입니다.")
	String receiverCompanyName,

	@NotNull(message = "공급업체 ID는 필수입니다.")
	UUID supplierCompanyId,

	@NotBlank(message = "공급업체 이름은 필수입니다.")
	String supplierCompanyName,

	String request,
	String deliveryRequest,

	@NotBlank(message = "주소는 필수입니다.")
	String address,

	@NotBlank(message = "상세주소는 필수입니다.")
	String addressDetail,

	@NotBlank(message = "우편번호는 필수입니다.")
	String zipCode,

	@NotNull(message = "위도는 필수입니다.")
	Double latitude,

	@NotNull(message = "경도는 필수입니다.")
	Double longitude,

	@NotNull(message = "총액은 필수입니다.")
	@PositiveOrZero(message = "총액은 0 이상이어야 합니다.")
	BigDecimal totalPrice,

	@NotEmpty(message = "주문 상품 목록은 비어 있을 수 없습니다.")
	@Valid
	List<CreateOrderItemRequest> orderItems
) {
}