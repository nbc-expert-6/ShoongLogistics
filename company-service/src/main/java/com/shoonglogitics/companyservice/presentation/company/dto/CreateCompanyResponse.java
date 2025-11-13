package com.shoonglogitics.companyservice.presentation.company.dto;

import java.util.UUID;

public record CreateCompanyResponse(
	UUID companyId,
	String message
) {}
