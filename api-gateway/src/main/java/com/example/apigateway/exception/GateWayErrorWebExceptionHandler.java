package com.example.apigateway.exception;

import java.nio.charset.StandardCharsets;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class GateWayErrorWebExceptionHandler implements ErrorWebExceptionHandler {

	private final ObjectMapper objectMapper;

	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		if (ex instanceof Exception) {
			log.error("Gateway Error : {}", ex.getMessage(), ex);
			exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);

			ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.UNAUTHORIZED, ex);

			String json;
			try {
				json = objectMapper.writeValueAsString(errorResponse);
			} catch (JsonProcessingException e) {
				log.error("Error serializing response body", e);
				json = "{\"message\": \"Internal Server Error\"}";
			}

			DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();
			DataBuffer buffer = bufferFactory.wrap(json.getBytes(StandardCharsets.UTF_8));

			return exchange.getResponse().writeWith(Mono.just(buffer));
		}

		return Mono.error(ex);
	}

}
