package com.shoonglogitics.companyservice.application.service;

import java.util.UUID;

import com.shoonglogitics.companyservice.application.service.dto.ProductCategoryInfo;

public interface ProductCategoryClient {
	ProductCategoryInfo getProductCategoryInfo(UUID productCategoryId, Long userId);
}
