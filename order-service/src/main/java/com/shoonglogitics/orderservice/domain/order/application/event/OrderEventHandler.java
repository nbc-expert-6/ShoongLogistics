package com.shoonglogitics.orderservice.domain.order.application.event;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.shoonglogitics.orderservice.domain.order.application.service.DeliveryClient;
import com.shoonglogitics.orderservice.domain.order.application.service.dto.DeliveryInfo;
import com.shoonglogitics.orderservice.domain.order.domain.event.OrderCreatedEvent;
import com.shoonglogitics.orderservice.domain.order.domain.event.OrderUpdatedEvent;
import com.shoonglogitics.orderservice.global.common.vo.AuthUser;
import com.shoonglogitics.orderservice.global.common.vo.UserRoleType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventHandler {

	private final DeliveryClient deliveryClient;

	/*
	주문 생성 이벤트 처리
	- 배송 생성 요청
	 */
	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleOrderCreated(OrderCreatedEvent event) {
		//배송 생성 요청
		//최소한의 정보를 보내고 배송을 생성할 때 Order에 요청하여 정보를 받아가도록 설계
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			throw new IllegalStateException("인증되지 않은 요청입니다.");
		}
		Object principal = authentication.getPrincipal();

		if (principal instanceof AuthUser authUser) {
			try {
				Long userId = authUser.getUserId();
				UserRoleType role = authUser.getRole();
				deliveryClient.createDelivery(event.getOrderId(), userId, role);
			} catch (Exception e) {
				log.error("배송 생성 중 에러 발생 error: {}", e.getMessage());
			}
		}
	}

	/*
	주문 수정 이벤트 처리
	- 배송 요청 사항이 수정되면 수정요청
	 */
	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleOrderUpdated(OrderUpdatedEvent event) {
		//배송 생성 요청
		//최소한의 정보를 보내고 배송을 생성할 때 Order에 요청하여 정보를 받아가도록 설계
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			throw new IllegalStateException("인증되지 않은 요청입니다.");
		}
		Object principal = authentication.getPrincipal();

		if (principal instanceof AuthUser authUser) {
			try {
				Long userId = authUser.getUserId();
				UserRoleType role = authUser.getRole();
				DeliveryInfo deliveryInfo = deliveryClient.getDelivery(event.getOrderId(), userId, role);
				deliveryClient.updateDelivery(deliveryInfo.deliveryId(), event.getDeliveryRequest(), userId, role);
			} catch (Exception e) {
				log.error("배송 조회 및 수정 중 에러 발생 error: {}", e.getMessage());
			}
		}
	}
}
