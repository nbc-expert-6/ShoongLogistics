package com.shoonglogitics.orderservice.domain.delivery.application.event;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.shoonglogitics.orderservice.domain.delivery.application.service.UserClient;
import com.shoonglogitics.orderservice.domain.delivery.domain.event.DeliveryCreatedEvent;
import com.shoonglogitics.orderservice.domain.common.vo.AuthUser;
import com.shoonglogitics.orderservice.domain.common.vo.UserRoleType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryEventHandler {

	private final UserClient userClient;

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleOrderCreated(DeliveryCreatedEvent event) {
		//배정한 배송 담당자 배송불가능 하도록 수정 요청
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			throw new IllegalStateException("인증되지 않은 요청입니다.");
		}
		Object principal = authentication.getPrincipal();

		if (principal instanceof AuthUser authUser) {
			try {
				Long userId = authUser.getUserId();
				UserRoleType role = authUser.getRole();
				userClient.changeShippersIsAvailable(event.getShippers(), userId, role);
				log.info("Received order created event: {}", event.getShippers());
			} catch (Exception e) {
				log.error("담당자 상태 변경 이벤트 처리 중 예외 발생 error: {}", e.getMessage());
			}
		}

	}
}
