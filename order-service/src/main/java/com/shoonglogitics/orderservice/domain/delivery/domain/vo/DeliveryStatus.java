package com.shoonglogitics.orderservice.domain.delivery.domain.vo;

import lombok.Getter;

@Getter
public enum DeliveryStatus {
	HUB_WAITING("허브 대기중") {
		@Override
		public boolean canTransitionTo(DeliveryStatus newStatus) {
			return newStatus == HUB_TRANSIT;
		}

		@Override
		public boolean canBeCancelled() {
			return true;
		}
	},
	HUB_TRANSIT("허브 이동중") {
		@Override
		public boolean canTransitionTo(DeliveryStatus newStatus) {
			return newStatus == HUB_ARRIVED;
		}

		@Override
		public boolean canBeCancelled() {
			return false;
		}
	},
	HUB_ARRIVED("목적지 허브 도착") {
		@Override
		public boolean canTransitionTo(DeliveryStatus newStatus) {
			return newStatus == IN_DELIVERY;
		}

		@Override
		public boolean canBeCancelled() {
			return false;
		}
	},
	IN_DELIVERY("배송중") {
		@Override
		public boolean canTransitionTo(DeliveryStatus newStatus) {
			return newStatus == DELIVERED;
		}

		@Override
		public boolean canBeCancelled() {
			return false;
		}
	},
	DELIVERED("배송완료") {
		@Override
		public boolean canTransitionTo(DeliveryStatus newStatus) {
			return false; // 완료 상태에서 전이 불가
		}

		@Override
		public boolean canBeCancelled() {
			return false;
		}
	};

	private final String description;

	DeliveryStatus(String description) {
		this.description = description;
	}

	public abstract boolean canTransitionTo(DeliveryStatus newStatus);

	public abstract boolean canBeCancelled();
}