package com.example.apigateway.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

	int getCode();

	String getMessage();

	HttpStatus getStatus();

	default int getHttpStatus() {
		return getStatus().value();
	}

}
