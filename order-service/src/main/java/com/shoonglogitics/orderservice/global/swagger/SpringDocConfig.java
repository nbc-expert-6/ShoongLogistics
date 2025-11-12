package com.shoonglogitics.orderservice.global.swagger;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SpringDocConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.info(new Info()
				.title("Order & Delivery Service API")
				.version("1.0")
				.description("주문 및 배송 서비스"))
			// ✅ Gateway를 통해 호출하도록 설정
			.servers(List.of(
				new Server()
					.url("http://localhost:8000")
					.description("API Gateway")
			))
			.addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
			.components(new Components()
				.addSecuritySchemes("Bearer Authentication", new SecurityScheme()
					.type(SecurityScheme.Type.HTTP)
					.scheme("bearer")
					.bearerFormat("JWT")));
	}
	//
	// @Bean
	// public GroupedOpenApi orderApi() {
	// 	return GroupedOpenApi.builder()
	// 		.group("order-service")
	// 		.pathsToMatch("/api/v1/orders/**") // 주문 관련 엔드포인트
	// 		.build();
	// }
	//
	// @Bean
	// public GroupedOpenApi deliveryApi() {
	// 	return GroupedOpenApi.builder()
	// 		.group("delivery-service")
	// 		.pathsToMatch("/api/v1/deliveries/**") // 배송 관련 엔드포인트
	// 		.build();
	// }
}