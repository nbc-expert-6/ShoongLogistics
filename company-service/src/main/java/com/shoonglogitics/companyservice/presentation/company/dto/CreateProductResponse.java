package com.shoonglogitics.companyservice.presentation.company.dto;

import java.util.UUID;

public record CreateProductResponse(
	UUID id,
	String message
) {
}
