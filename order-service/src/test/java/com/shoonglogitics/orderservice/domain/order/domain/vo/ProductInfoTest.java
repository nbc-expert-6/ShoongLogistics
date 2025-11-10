package com.shoonglogitics.orderservice.domain.order.domain.vo;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductInfoTest {

	@Test
	@DisplayName("유효한 productId와 price로 ProductInfo를 생성할 수 있어야 한다")
	void productInfo_withValidProductIdAndPrice_ShouldBeCreated() {
		// Given
		UUID productId = UUID.randomUUID();
		Money price = Money.of(new BigDecimal("1000.00"));

		// When
		ProductInfo productInfo = ProductInfo.of(productId, price);

		// Then
		assertThat(productInfo.getProductId()).isEqualTo(productId);
		assertThat(productInfo.getPrice()).isEqualTo(price);
	}

}