package com.shoonglogitics.companyservice.presentation.company.dto;

import java.util.UUID;

public record CreateCompanyResponse(
	UUID orderId,
	String message
) {}
