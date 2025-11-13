package com.shoonglogitics.companyservice.application.service;

import java.util.List;
import java.util.UUID;

import com.shoonglogitics.companyservice.application.service.dto.ProductInfo;

public interface CompanyClient {
	List<ProductInfo> getProductInfos(UUID productCategoryId, Long userId);
	boolean hasProductsInCategory(UUID productCategoryId, Long userId);
}
