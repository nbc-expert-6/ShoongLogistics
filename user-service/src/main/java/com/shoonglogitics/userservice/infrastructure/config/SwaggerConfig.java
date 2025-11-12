package com.shoonglogitics.userservice.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

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
			.description("JWT 토큰을 입력하세요 (예: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...)");

		SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);

		return new OpenAPI()
			.info(new Info()
				.title("User Service API")
				.description("JWT 인증이 적용된 Swagger 문서 예시")
				.version("v1.0"))
			.addSecurityItem(securityRequirement)
			.components(new Components().addSecuritySchemes(jwtSchemeName, securityScheme));
	}
}
