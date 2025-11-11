package com.shoonglogitics.companyservice.infrastructure.lock;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class DistributedLockAspect {
	private final RedissonClient redissonClient;
	private final ExpressionParser parser = new SpelExpressionParser();

	@Around("@annotation(distributedLock)")
	public Object lock(ProceedingJoinPoint joinPoint, DistributedLock distributedLock) throws Throwable {
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		Method method = signature.getMethod();
		String lockKey = generateKey(distributedLock.key(), joinPoint, method);

		RLock lock = redissonClient.getLock(lockKey);

		try {
			boolean available = lock.tryLock(
				distributedLock.waitTime(),
				distributedLock.leaseTime(),
				distributedLock.timeUnit()
			);

			if (!available) {
				log.warn("락 획득 실패: {}", lockKey);
				throw new IllegalStateException("락을 획득할 수 없습니다. 잠시 후 다시 시도해주세요.");
			}

			log.debug("락 획득 성공: {}", lockKey);
			return joinPoint.proceed();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new IllegalStateException("락 획득 중 인터럽트 발생", e);
		} finally {
			if (lock.isHeldByCurrentThread()) {
				lock.unlock();
				log.debug("락 해제: {}", lockKey);
			}
		}
	}

	private String generateKey(String key, ProceedingJoinPoint joinPoint, Method method) {
		// SpEL 표현식 파싱
		StandardEvaluationContext context = new StandardEvaluationContext();
		
		// 메서드 파라미터를 SpEL 컨텍스트에 추가
		String[] parameterNames = signature(joinPoint).getParameterNames();
		Object[] args = joinPoint.getArgs();
		
		for (int i = 0; i < parameterNames.length; i++) {
			context.setVariable(parameterNames[i], args[i]);
		}

		return parser.parseExpression(key).getValue(context, String.class);
	}

	private MethodSignature signature(ProceedingJoinPoint joinPoint) {
		return (MethodSignature)joinPoint.getSignature();
	}
}
