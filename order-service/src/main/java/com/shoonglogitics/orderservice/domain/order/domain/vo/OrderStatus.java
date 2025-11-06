package com.shoonglogitics.orderservice.domain.order.domain.vo;

import lombok.Getter;

@Getter
public enum OrderStatus {
	PENDING("주문 대기") {
		@Override
		public boolean canTransitionTo(OrderStatus newStatus) {
			return newStatus == PAID || newStatus == CANCELLED;
		}

		@Override
		public boolean canBeCancelled() {
			return true;
		}
	},

	PAID("결제 완료") {
		@Override
		public boolean canTransitionTo(OrderStatus newStatus) {
			return newStatus == SHIPPED || newStatus == CANCELLED;
		}

		@Override
		public boolean canBeCancelled() {
			return true;
		}
	},

	SHIPPED("배송 중") {
		@Override
		public boolean canTransitionTo(OrderStatus newStatus) {
			return newStatus == DELIVERED;
		}

		@Override
		public boolean canBeCancelled() {
			return false;
		}
	},

	DELIVERED("배송 완료") {
		@Override
		public boolean canTransitionTo(OrderStatus newStatus) {
			return false;
		}

		@Override
		public boolean canBeCancelled() {
			return false;
		}
	},

	CANCELLED("취소됨") {
		@Override
		public boolean canTransitionTo(OrderStatus newStatus) {
			return false;
		}

		@Override
		public boolean canBeCancelled() {
			return false;
		}
	};

	private final String description;

	OrderStatus(String description) {
		this.description = description;
	}

	public abstract boolean canTransitionTo(OrderStatus newStatus);

	public abstract boolean canBeCancelled();

	public boolean canBePaid() {
		return this == PENDING;
	}

	public boolean canBeShipped() {
		return this == PAID;
	}
}
