package com.shoonglogitics.companyservice.domain.company.entity;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.shoonglogitics.companyservice.domain.company.vo.ProductInfo;

class ProductTest {
	@Test
	@DisplayName("유효한 정보로 상품을 생성할 수 있다")
	void createProduct() {
		// Given
		UUID categoryId = UUID.randomUUID();
		String name = "노트북";
		Integer price = 1500000;
		String description = "고성능 업무용 노트북";

		// When
		Product product = Product.create(categoryId, ProductInfo.of(name, price, description));

		// Then
		assertThat(product.getProductInfo().getName()).isEqualTo(name);
		assertThat(product.getProductInfo().getPrice()).isEqualTo(price);
		assertThat(product.getProductInfo().getDescription()).isEqualTo(description);
	}

	@Test
	@DisplayName("상품 정보를 수정할 수 있다")
	void updateProduct() {
		// Given
		UUID categoryId = UUID.randomUUID();
		Product product = Product.create(
			categoryId,
			ProductInfo.of("노트북",
			1500000,
			"고성능 업무용 노트북")
		);
		UUID newCategoryId = UUID.randomUUID();
		// When
		product.update(newCategoryId, ProductInfo.of("울트라북", 2000000, "초경량 울트라북"));

		// Then
		assertThat(product.getProductCategoryId()).isNotEqualTo(categoryId);
		assertThat(product.getName()).isEqualTo("울트라북");
		assertThat(product.getPrice()).isEqualTo(2000000);
		assertThat(product.getDescription()).isEqualTo("초경량 울트라북");
	}

	@Test
	@DisplayName("이미 삭제된 상품은 다시 삭제할 수 없다")
	void deleteAlreadyDeletedProduct() {
		// Given
		Product product = Product.create(
			UUID.randomUUID(),
			ProductInfo.of("노트북",
			1500000,
			"고성능 업무용 노트북")
		);
		product.softDelete(1L);

		// When & Then
		assertThatThrownBy(() -> product.softDelete(2L))
			.isInstanceOf(IllegalArgumentException.class);
	}
}
