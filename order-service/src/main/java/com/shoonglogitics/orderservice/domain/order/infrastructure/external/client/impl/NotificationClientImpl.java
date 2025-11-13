package com.shoonglogitics.orderservice.domain.order.infrastructure.external.client.impl;

import org.springframework.stereotype.Component;

import com.shoonglogitics.orderservice.domain.order.application.service.NotificationClient;
import com.shoonglogitics.orderservice.domain.order.infrastructure.external.client.feign.NotificationFeignClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component("orderNotificationClientImpl")
@RequiredArgsConstructor
@Slf4j
public class NotificationClientImpl implements NotificationClient {
	private final NotificationFeignClient notificationFeignClient;
}
