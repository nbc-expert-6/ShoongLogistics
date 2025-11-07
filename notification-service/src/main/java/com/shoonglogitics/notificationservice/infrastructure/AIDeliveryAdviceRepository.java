package com.shoonglogitics.notificationservice.infrastructure;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shoonglogitics.notificationservice.domain.entity.AIDeliveryAdvice;

public interface AIDeliveryAdviceRepository extends JpaRepository<AIDeliveryAdvice, UUID> {
}
