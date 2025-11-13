package com.shoonglogitics.companyservice.presentation.company.dto;

import java.util.UUID;

public record UpdateProductResponse(
	UUID id,
	String message
) {
}
