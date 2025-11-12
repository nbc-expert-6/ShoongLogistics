package com.shoonglogitics.companyservice.infrastructure.config;

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
		return new OpenAPI()
			.info(new Info()
				.title("Company Service API")
				.version("1.0")
				.description("업체 관리 서비스"))
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
}
