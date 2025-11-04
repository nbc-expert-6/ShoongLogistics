package com.shoonglogitics.orderservice.delivery.domain.entity;

import com.shoonglogitics.orderservice.common.entity.BaseAggregateRoot;
import com.shoonglogitics.orderservice.delivery.domain.vo.DeliveryStatus;
import com.shoonglogitics.orderservice.delivery.domain.vo.HubId;
import com.shoonglogitics.orderservice.delivery.domain.vo.ShipperInfo;
import com.shoonglogitics.orderservice.order.domain.vo.Address;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "p_delivery")
@Where(clause = "deleted_at IS NULL")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery extends BaseAggregateRoot<Delivery> {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(name = "id",columnDefinition = "uuid")
    private UUID id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @Column(name = "request")
    private String request;

    @Embedded
    private Address address;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "shipperId", column = @Column(name = "shipper_id")),
            @AttributeOverride(name = "shipperName", column = @Column(name = "shipper_name")),
            @AttributeOverride(name = "shipperPhoneNumber", column = @Column(name = "shipper_phone_number"))
    })
    private ShipperInfo shipperInfo;

    @Embedded
    @AttributeOverride(name = "hubId", column = @Column(name = "departure_hub_id"))
    private HubId departureHubId;

    @Embedded
    @AttributeOverride(name = "hubId", column = @Column(name = "destination_hub_id"))
    private HubId destinationHubId;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private List<DeliveryRoute> deliveryRoutes = new ArrayList<>();

    //Todo 생성시 검증 로직 추가
    public static Delivery create(
            Address address,
            ShipperInfo shipperInfo,
            HubId departureHubId,
            HubId destinationHubId,
            String request
    ) {
        Delivery delivery = new Delivery();
        delivery.status = DeliveryStatus.HUB_WAITING;
        delivery.address = address;
        delivery.shipperInfo = shipperInfo;
        delivery.departureHubId = departureHubId;
        delivery.destinationHubId = destinationHubId;
        delivery.request = request;
        return delivery;
    }
}
