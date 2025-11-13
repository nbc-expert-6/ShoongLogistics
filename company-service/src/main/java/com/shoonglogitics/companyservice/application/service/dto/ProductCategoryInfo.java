package com.shoonglogitics.companyservice.application.service.dto;

import java.util.UUID;

public record ProductCategoryInfo(
	UUID productCategoryId,
	String name
) {
}
