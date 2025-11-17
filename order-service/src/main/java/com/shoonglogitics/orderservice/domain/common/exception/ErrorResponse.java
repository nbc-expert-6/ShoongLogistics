package com.shoonglogitics.orderservice.domain.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

public record ErrorResponse<T>(
	String code,
	String message,
	Integer status
) {

}
