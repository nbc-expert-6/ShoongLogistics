package com.shoonglogitics.orderservice.domain.order.infrastructure.repository.audit;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

@Configuration
public class TestAuditorConfig {

	@Bean
	public AuditorAware<Long> auditorAwareImpl() {
		// 테스트용으로 고정값 반환
		return () -> Optional.of(1L);
	}
}