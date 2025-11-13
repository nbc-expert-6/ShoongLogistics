package com.shoonglogitics.companyservice.helper;

import java.util.List;

import org.springframework.data.domain.AbstractAggregateRoot;

public final class DomainEventTestHelper {

	private DomainEventTestHelper() {}

	@SuppressWarnings("unchecked")
	public static <T extends AbstractAggregateRoot<T>> List<Object> extractDomainEvents(AbstractAggregateRoot<T> aggregateRoot) {
		try {
			var method = AbstractAggregateRoot.class.getDeclaredMethod("domainEvents");
			method.setAccessible(true);
			return (List<Object>) method.invoke(aggregateRoot);
		} catch (Exception e) {
			throw new RuntimeException("도메인 이벤트를 추출하는 중 오류 발생", e);
		}
	}
}