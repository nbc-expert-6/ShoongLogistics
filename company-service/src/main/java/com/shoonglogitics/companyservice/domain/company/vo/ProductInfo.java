package com.shoonglogitics.companyservice.domain.company.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductInfo {

	private String name;
	private Integer price;
	private String description;

	private ProductInfo(String name, Integer price, String description) {
		validatePrice(price);

		this.name = name;
		this.price = price;
		this.description = description;
	}

	public static ProductInfo of(String name, Integer price, String description) {
		return new ProductInfo(name, price, description);
	}

	private void validatePrice(Integer price) {
		if (price < 0) {
			throw new IllegalArgumentException("상품가격은 음수일 수 없습니다.");
		}
	}
}
