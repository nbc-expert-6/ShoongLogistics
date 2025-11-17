package com.shoonglogitics.orderservice.global.config;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableAsync
@Slf4j
public class AsyncConfig implements AsyncConfigurer {

	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

		// CorePoolSize: 기본적으로 유지할 스레드 개수
		executor.setCorePoolSize(5);

		// MaxPoolSize: 최대 스레드 개수
		executor.setMaxPoolSize(10);

		// QueueCapacity: 작업 대기 큐 크기
		// CorePoolSize를 초과하는 작업을 저장해둘 큐의 크기
		executor.setQueueCapacity(100);

		// 스레드 이름 prefix 설정
		// 로그에서 "event-async-1", "event-async-2" 같은 이름으로 표시됩니다.
		executor.setThreadNamePrefix("event-async-");

		executor.initialize();
		return executor;
	}

	// 비동기 작업 중 발생한 예외를 처리합니다.
	// 이를 설정하지 않으면 예외가 조용히 사라져 디버깅이 어려워집니다.
	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return (ex, method, params) -> {
			log.error("비동기 작업 중 예외 발생 - Method: {}, Exception: {}",
				method.getName(), ex.getMessage());
		};
	}
}
