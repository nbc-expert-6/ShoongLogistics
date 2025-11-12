package com.shoonglogitics.orderservice.domain.delivery.domain.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.Where;

import com.shoonglogitics.orderservice.domain.delivery.domain.event.DeliveryCreatedEvent;
import com.shoonglogitics.orderservice.domain.delivery.domain.vo.Address;
import com.shoonglogitics.orderservice.domain.delivery.domain.vo.DeliveryStatus;
import com.shoonglogitics.orderservice.domain.delivery.domain.vo.HubInfo;
import com.shoonglogitics.orderservice.domain.delivery.domain.vo.ShipperInfo;
import com.shoonglogitics.orderservice.global.common.entity.BaseAggregateRoot;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_delivery")
@Where(clause = "deleted_at IS NULL")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery extends BaseAggregateRoot<Delivery> {

	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	@Column(name = "id", columnDefinition = "uuid")
	private UUID id;

	@Column(name = "order_id", nullable = false)
	private UUID orderId;

	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private DeliveryStatus status;

	@Column(name = "request")
	private String request;

	@Embedded
	private com.shoonglogitics.orderservice.domain.delivery.domain.vo.Address address;

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
	@AttributeOverride(name = "hubId", column = @Column(name = "destination_hub_id"))
	private HubInfo destinationHubId;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "delivery_id")
	private List<DeliveryRoute> deliveryRoutes = new ArrayList<>();

	public static Delivery create(
		UUID orderId,
		Address address,
		ShipperInfo shipperInfo,
		HubInfo departureHubId,
		HubInfo destinationHubId,
		String request,
		List<DeliveryRoute> deliveryRoutes
	) {
		if (orderId == null) {
			throw new IllegalArgumentException("주문 번호(OrderId)는 필수입니다.");
		}
		if (address == null) {
			throw new IllegalArgumentException("배송지 주소(Address)는 필수입니다.");
		}
		if (shipperInfo == null) {
			throw new IllegalArgumentException("배송 담당자(ShipperInfo)는 필수입니다.");
		}
		if (departureHubId == null) {
			throw new IllegalArgumentException("출발 허브(HubInfo)는 필수입니다.");
		}
		if (destinationHubId == null) {
			throw new IllegalArgumentException("도착 허브(HubInfo)는 필수입니다.");
		}

		Delivery delivery = new Delivery();
		delivery.orderId = orderId;
		delivery.status = DeliveryStatus.HUB_WAITING;
		delivery.address = address;
		delivery.shipperInfo = shipperInfo;
		delivery.departureHubId = departureHubId;
		delivery.destinationHubId = destinationHubId;
		delivery.request = request;
		delivery.deliveryRoutes = deliveryRoutes;

		delivery.registerEvent(
			new DeliveryCreatedEvent(delivery.createAssginedShippers(deliveryRoutes, shipperInfo.getShipperId())));
		return delivery;
	}

	//배송 생성 이벤트에 상태변경 요청할 담당자 목록 생성
	private List<Long> createAssginedShippers(List<DeliveryRoute> deliveryRoutes, Long shipperId) {
		List<Long> assginedShippers = new ArrayList<>();
		deliveryRoutes.stream().map(deliveryRoute -> shipperInfo.getShipperId()).forEach(assginedShippers::add);
		assginedShippers.add(shipperId);
		return assginedShippers;
	}

	public void update(
		String request,
		Long shipperId,
		String shipperName,
		String shipperPhoneNumber,
		String shipperSlackId
	) {
		if (this.status == DeliveryStatus.DELIVERED) {
			throw new IllegalArgumentException("배송이 완료되어 수정할 수 없습니다.");
		}

		if (request != null) {
			this.request = request;
		}

		this.shipperInfo.update(
			shipperId,
			shipperName,
			shipperPhoneNumber,
			shipperSlackId
		);
	}

	public void delete(Long userId) {
		if (this.status != DeliveryStatus.HUB_WAITING) {
			throw new IllegalStateException("배송 중인 상태에선 삭제할 수 없습니다.");
		}

		if (userId == null) {
			throw new IllegalArgumentException("사용자 정보가 존재하지 않습니다.");
		}

		this.deliveryRoutes.forEach(deliveryRoute -> {
			deliveryRoute.softDelete(userId);
		});
		this.status = DeliveryStatus.CANCLED;
		this.softDelete(userId);
	}

	public void startHubTransit() {
		if (!this.status.canTransitionTo(DeliveryStatus.HUB_TRANSIT)) {
			throw new IllegalStateException("허브 운송 대기 상태에서만 허브 운송처리 할 수 있습니다.");
		}
		this.status = DeliveryStatus.HUB_TRANSIT;
	}

	public void completeHubTransit() {
		if (!this.status.canTransitionTo(DeliveryStatus.DESTINATION_HUB_ARRIVED)) {
			throw new IllegalStateException("허브 운송 중인 상태에서만 목적지 허브 도착처리 할 수 있습니다.");
		}
		this.status = DeliveryStatus.DESTINATION_HUB_ARRIVED;
	}

	public void startDelivery() {
		if (!this.status.canTransitionTo(DeliveryStatus.IN_DELIVERY)) {
			throw new IllegalStateException("허브 운송이 완료된 상태에서만 배송 출발처리 할 수 있습니다.");
		}
		this.status = DeliveryStatus.IN_DELIVERY;
	}

	public void completeDelivery() {
		if (!this.status.canTransitionTo(DeliveryStatus.DELIVERED)) {
			throw new IllegalStateException("배송중인 상태에서만 배송 완료처리 할 수 있습니다.");
		}
		this.status = DeliveryStatus.DELIVERED;
	}
}
