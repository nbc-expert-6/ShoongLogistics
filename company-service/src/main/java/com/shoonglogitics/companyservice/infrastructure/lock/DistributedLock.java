package com.shoonglogitics.companyservice.infrastructure.lock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DistributedLock {
	/**
	 * 락 이름
	 */
	String key();

	/**
	 * 락 대기 시간 (기본 5초)
	 */
	long waitTime() default 5L;

	/**
	 * 락 임대 시간 (기본 3초)
	 */
	long leaseTime() default 3L;

	/**
	 * 시간 단위 (기본 초)
	 */
	TimeUnit timeUnit() default TimeUnit.SECONDS;
}
