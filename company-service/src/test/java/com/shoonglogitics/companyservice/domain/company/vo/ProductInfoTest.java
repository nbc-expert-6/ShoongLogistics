package com.shoonglogitics.companyservice.domain.company.vo;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 값 객체 테스트 - ProductInfo
 */
class ProductInfoTest {

	@Test
	@DisplayName("유효한 상품 정보로 ProductInfo를 생성할 수 있다")
	void createValidProductInfo() {
		// Given
		String name = "노트북";
		Integer price = 1500000;
		String description = "고성능 업무용 노트북";

		// When
		ProductInfo productInfo = ProductInfo.of(name, price, description);

		// Then
		assertThat(productInfo.getName()).isEqualTo(name);
		assertThat(productInfo.getPrice()).isEqualTo(price);
		assertThat(productInfo.getDescription()).isEqualTo(description);
	}

	@Test
	@DisplayName("가격이 0원인 상품을 생성할 수 있다")
	void createFreeProduct() {
		// When
		ProductInfo productInfo = ProductInfo.of("무료 샘플", 0, "무료 증정품");

		// Then
		assertThat(productInfo.getPrice()).isEqualTo(0);
	}

	@Test
	@DisplayName("음수 가격으로는 상품을 생성할 수 없다")
	void negativePrice() {
		// When & Then
		assertThatThrownBy(() -> ProductInfo.of("노트북", -1000, "설명"))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("상품가격은 음수일 수 없습니다");
	}

	@Test
	@DisplayName("같은 상품 정보의 ProductInfo 객체는 동일하다")
	void equality() {
		// Given
		ProductInfo product1 = ProductInfo.of("노트북", 1500000, "고성능 노트북");
		ProductInfo product2 = ProductInfo.of("노트북", 1500000, "고성능 노트북");

		// Then
		assertThat(product1).isEqualTo(product2);
		assertThat(product1.hashCode()).isEqualTo(product2.hashCode());
	}

	@Test
	@DisplayName("이름이 같아도 가격이 다르면 동일하지 않다")
	void inequality_differentPrice() {
		// Given
		ProductInfo product1 = ProductInfo.of("노트북", 1500000, "설명");
		ProductInfo product2 = ProductInfo.of("노트북", 2000000, "설명");

		// Then
		assertThat(product1).isNotEqualTo(product2);
	}
}
