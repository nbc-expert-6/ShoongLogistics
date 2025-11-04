package com.shoonglogitics.companyservice.domain.common.entity;

import java.time.LocalDateTime;

public interface BaseAuditEntity {
	LocalDateTime getCreatedAt();
	Long getCreatedBy();
	LocalDateTime getUpdatedAt();
	Long getUpdatedBy();
	LocalDateTime getDeletedAt();
	Long getDeletedBy();

	void update(Long userId);
	void softDelete(Long userId);
	boolean isDeleted();
}