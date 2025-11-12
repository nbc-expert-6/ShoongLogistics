package com.shoonglogitics.userservice.infrastructure.config;

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
public class SwaggerConfig {
	@Bean
	public OpenAPI openAPI() {
		String jwtSchemeName = "BearerAuth";

		SecurityScheme securityScheme = new SecurityScheme()
			.name(jwtSchemeName)
			.type(SecurityScheme.Type.HTTP)
			.scheme("bearer")
			.bearerFormat("JWT")
			.description("JWT 토큰을 입력하세요 (Bearer 제외)");

		SecurityRequirement securityRequirement = new SecurityRequirement()
			.addList(jwtSchemeName);

		return new OpenAPI()
			.info(new Info()
				.title("User Service API")
				.description("사용자 관리 서비스")
				.version("1.0"))
			.servers(List.of(
				new Server()
					.url("http://localhost:8000")
					.description("API Gateway")
			))
			.addSecurityItem(securityRequirement)
			.components(new Components()
				.addSecuritySchemes(jwtSchemeName, securityScheme));
	}
}
