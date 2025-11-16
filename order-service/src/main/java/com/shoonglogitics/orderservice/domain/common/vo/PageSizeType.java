package com.shoonglogitics.orderservice.domain.common.vo;

import lombok.Getter;

@Getter
public enum PageSizeType {
	SIZE_10(10),
	SIZE_30(30),
	SIZE_50(50);

	private final int value;

	PageSizeType(int value) {
		this.value = value;
	}
}