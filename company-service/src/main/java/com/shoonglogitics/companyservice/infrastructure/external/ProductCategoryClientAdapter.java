package com.shoonglogitics.companyservice.infrastructure.external;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.shoonglogitics.companyservice.application.service.ProductCategoryClient;
import com.shoonglogitics.companyservice.application.service.dto.ProductCategoryInfo;
import com.shoonglogitics.companyservice.domain.common.vo.UserRoleType;
import com.shoonglogitics.companyservice.infrastructure.external.dto.ProductCategoryInfoFeignClientResponse;
import com.shoonglogitics.companyservice.presentation.common.dto.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductCategoryClientAdapter implements ProductCategoryClient {

	private final ProductCategoryFeignClient productCategoryFeignClient;

	@Override
	public ProductCategoryInfo getProductCategoryInfo(UUID producatCategoryId, Long userId) {
		ApiResponse<ProductCategoryInfoFeignClientResponse> response = productCategoryFeignClient.getProductCategory(producatCategoryId, userId,
			UserRoleType.MASTER.getAuthority());

		if (!response.success() || response.data() == null) {
			log.warn("카테고리 정보 조회 실패 - message: {}", response.message());
			throw new IllegalArgumentException(response.message());
		}

		return toProductCategoryInfo(response.data());
	}

	private ProductCategoryInfo toProductCategoryInfo(ProductCategoryInfoFeignClientResponse response) {
		return new ProductCategoryInfo(response.productCategoryId(), response.name());
	}
}
