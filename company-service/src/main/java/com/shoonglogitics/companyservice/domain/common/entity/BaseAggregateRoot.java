package com.shoonglogitics.companyservice.domain.common.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseAggregateRoot<A extends BaseAggregateRoot<A>>
	extends AbstractAggregateRoot<A>
	implements BaseAuditEntity {

	@CreatedDate
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@CreatedBy
	@Column(name = "created_by", updatable = false)
	private Long createdBy;

	@LastModifiedDate
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@LastModifiedBy
	@Column(name = "updated_by")
	private Long updatedBy;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Column(name = "deleted_by")
	private Long deletedBy;

	@Override
	public void update(Long userId) {
		this.updatedAt = LocalDateTime.now();
		this.updatedBy = userId;
	}

	@Override
	public void softDelete(Long userId) {
		this.deletedAt = LocalDateTime.now();
		this.deletedBy = userId;
	}

	@Override
	public boolean isDeleted() {
		return this.deletedAt != null;
	}
}
