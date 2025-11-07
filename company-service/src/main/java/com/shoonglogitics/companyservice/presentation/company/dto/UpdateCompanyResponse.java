package com.shoonglogitics.companyservice.presentation.company.dto;

import java.util.UUID;

public record UpdateCompanyResponse(
	UUID companyId,
	String message
) {
}
