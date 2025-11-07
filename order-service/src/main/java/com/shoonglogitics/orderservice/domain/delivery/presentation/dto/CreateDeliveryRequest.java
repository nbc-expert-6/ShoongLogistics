package com.shoonglogitics.orderservice.domain.delivery.presentation.dto;

import java.util.UUID;

public record CreateDeliveryRequest(
	UUID orderID
) {
}
