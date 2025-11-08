package com.shoonglogitics.orderservice.domain.delivery.domain.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.Where;

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
		@AttributeOverride(name = "shipperPhoneNumber", column = @Column(name = "shipper_phone_number"))
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

		delivery.registerEvent()
		return delivery;
	}
}
