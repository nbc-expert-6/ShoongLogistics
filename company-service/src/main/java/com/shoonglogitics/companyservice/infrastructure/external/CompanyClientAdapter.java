package com.shoonglogitics.companyservice.infrastructure.external;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.shoonglogitics.companyservice.application.service.CompanyClient;
import com.shoonglogitics.companyservice.application.service.dto.ProductInfo;
import com.shoonglogitics.companyservice.domain.common.vo.UserRoleType;
import com.shoonglogitics.companyservice.infrastructure.external.dto.ProductInfoFeignClientResponse;
import com.shoonglogitics.companyservice.presentation.common.dto.ApiResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CompanyClientAdapter implements CompanyClient {
	private final CompanyFeignClient companyFeignClient;

	@Override
	public List<ProductInfo> getProductInfos(UUID productCategoryId, Long userId) {
		ApiResponse<List<ProductInfoFeignClientResponse>> response = companyFeignClient.getProducts(
			List.of(productCategoryId), userId,
			UserRoleType.MASTER.getAuthority());

		if (!response.success() || response.data() == null) {
			log.warn("상품 조회 실패 - message: {}", response.message());
			throw new IllegalArgumentException(response.message());
		}
		return response.data().stream()
			.map(this::toProductInfo)
			.toList();
	}

	private ProductInfo toProductInfo(ProductInfoFeignClientResponse response) {
		return new ProductInfo(response.productCategoryId(), response.name());
	}
}
