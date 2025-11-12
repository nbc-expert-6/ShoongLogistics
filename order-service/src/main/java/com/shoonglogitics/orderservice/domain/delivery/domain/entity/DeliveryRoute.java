package com.shoonglogitics.orderservice.domain.delivery.domain.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.Where;

import com.shoonglogitics.orderservice.domain.delivery.domain.vo.DeliveryActual;
import com.shoonglogitics.orderservice.domain.delivery.domain.vo.DeliveryEstimate;
import com.shoonglogitics.orderservice.domain.delivery.domain.vo.DeliveryStatus;
import com.shoonglogitics.orderservice.domain.delivery.domain.vo.HubInfo;
import com.shoonglogitics.orderservice.domain.delivery.domain.vo.ShipperInfo;
import com.shoonglogitics.orderservice.global.common.entity.BaseEntity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_delivery_route")
@Where(clause = "deleted_at IS NULL")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryRoute extends BaseEntity {

	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	@Column(name = "id", columnDefinition = "uuid")
	private UUID id;

	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "shipperId", column = @Column(name = "shipper_id")),
		@AttributeOverride(name = "shipperName", column = @Column(name = "shipper_name")),
		@AttributeOverride(name = "shipperPhoneNumber", column = @Column(name = "shipper_phone_number")),
		@AttributeOverride(name = "shipperSlackId", column = @Column(name = "shipper_slack_id"))
	})
	private ShipperInfo shipperInfo;

	@Embedded
	@AttributeOverride(name = "hubId", column = @Column(name = "departure_hub_id"))
	private HubInfo departureHubId;

	@Embedded
	@AttributeOverride(name = "hubId", column = @Column(name = "arrival_hub_id"))
	private HubInfo arrivalHubId;

	@Column(name = "sequence", nullable = false)
	private Integer sequence;

	@Embedded
	DeliveryEstimate estimate;

	@Embedded
	DeliveryActual actual;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private DeliveryStatus status;

	public static DeliveryRoute create(
		ShipperInfo shipperInfo,
		HubInfo departureHubId,
		HubInfo arrivalHubId,
		Integer sequence,
		DeliveryEstimate estimate
	) {
		if (shipperInfo == null) {
			throw new IllegalArgumentException("배송 담당자(ShipperInfo)는 필수입니다.");
		}
		if (departureHubId == null) {
			throw new IllegalArgumentException("출발 허브(HubInfo)는 필수입니다.");
		}
		if (arrivalHubId == null) {
			throw new IllegalArgumentException("도착 허브(HubInfo)는 필수입니다.");
		}
		if (sequence == null || sequence <= 0) {
			throw new IllegalArgumentException("순번(sequence)은 1 이상의 값이어야 합니다.");
		}
		if (estimate == null) {
			throw new IllegalArgumentException("예상 배송 정보(DeliveryEstimate)는 필수입니다.");
		}

		DeliveryRoute route = new DeliveryRoute();
		route.shipperInfo = shipperInfo;
		route.departureHubId = departureHubId;
		route.arrivalHubId = arrivalHubId;
		route.sequence = sequence;
		route.estimate = estimate;
		route.status = DeliveryStatus.HUB_WAITING;
		return route;
	}

	public void departure() {
		if (this.status.canTransitionTo(DeliveryStatus.HUB_TRANSIT)) {
			this.status = DeliveryStatus.HUB_TRANSIT;
		}
	}

	public void arrive(Long distance, Integer duration) {
		if (!this.status.canTransitionTo(DeliveryStatus.ARRIVAL_HUB_ARRIVED)) {
			throw new IllegalStateException("허브 운송중인 상태에서만 도착 처리 할 수 있습니다.");
		}
		this.status = DeliveryStatus.ARRIVAL_HUB_ARRIVED;
		this.actual = DeliveryActual.of(distance, duration);
	}

}
