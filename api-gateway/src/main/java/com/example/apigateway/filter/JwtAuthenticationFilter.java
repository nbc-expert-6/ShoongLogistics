package com.example.apigateway.filter;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.example.apigateway.exception.CustomException;
import com.example.apigateway.exception.GatewayErrorCode;
import com.example.apigateway.util.TokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {

	private static final String USER_ID_HEADER = "X-User-Id";
	private static final String USER_ROLE_HEADER = "X-User-Role";
	private final TokenProvider tokenProvider;
	private final List<String> whiteList = List.of(
		"/api/v1/users/login",
		"/api/v1/users/signup",
		"/api/v1/users/internal",
		"/api/v1/ai-delivery/advice",
		"/swagger-ui/**",
		"/swagger-ui.html",
		"/swagger-ui/index.html",
		"/webjars/**",
		"/swagger-resources",
		"/v3/api-docs/**",
		"/favicon.ico",
		"/actuator",
		"/company-service/**",
		"/docs/**",
		"/springdoc/**"
	);

	private static ServerHttpRequest createCustomRequest(ServerWebExchange exchange, String userId,
		String userRole) {
		return exchange.getRequest().mutate()
			.header(USER_ID_HEADER, userId)
			.header(USER_ROLE_HEADER, userRole)
			.build();
	}

	private boolean isWhiteListPath(String requestPath) {
		return whiteList.stream().anyMatch(pattern -> {
			// 정확히 일치
			if (pattern.equals(requestPath)) {
				return true;
			}
			if (pattern.endsWith("/**")) {
				String prefix = pattern.substring(0, pattern.length() - 3);
				return requestPath.startsWith(prefix);
			}

			return requestPath.contains("internal");
		});
	}

	private String extractToken(String accessToken) {
		return accessToken.substring(7);
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		String requestPath = exchange.getRequest().getURI().getPath();

		if (isWhiteListPath(requestPath)) {
			return chain.filter(exchange);
		}

		String accessToken = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		log.debug("[JwtAuthenticationFilter]{}", accessToken);

		if (tokenProvider.validAccessToken(accessToken)) {
			accessToken = extractToken(accessToken);
			String userId = tokenProvider.getUserId(accessToken);
			String userRole = tokenProvider.getUserRole(accessToken);

			ServerHttpRequest modifiedRequest = createCustomRequest(exchange, userId, userRole);
			ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();

			return chain.filter(modifiedExchange);
		}
		throw CustomException.from(GatewayErrorCode.MISSING_AUTHORIZATION_HEADER);
	}

}
