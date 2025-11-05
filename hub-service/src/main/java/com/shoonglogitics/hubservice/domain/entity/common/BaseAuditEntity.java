package com.shoonglogitics.hubservice.domain.entity.common;

import java.time.LocalDateTime;

public interface BaseAuditEntity {
    LocalDateTime getCreatedAt();
    Long getCreatedBy();
    LocalDateTime getUpdatedAt();
    Long getUpdatedBy();
    LocalDateTime getDeletedAt();
    Long getDeletedBy();

    void softDelete(Long userId);
    boolean isDeleted();
}
