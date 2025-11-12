package com.shoonglogitics.orderservice.domain.order.infrastructure.repository.audit;

import java.util.Optional;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;

@TestConfiguration
public class TestAuditorConfig {

	@Bean
	@Primary
	public AuditorAware<Long> auditorAwareImpl() {
		// 테스트용으로 고정값 반환
		return () -> Optional.of(1L);
	}
}