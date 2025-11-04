package com.shoonglogitics.orderservice.delivery.domain.entity;

import com.shoonglogitics.orderservice.delivery.domain.vo.HubId;
import com.shoonglogitics.orderservice.delivery.domain.vo.ShipperInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.Where;

import java.util.UUID;

@Entity
@Table(name = "p_delivery")
@Where(clause = "deleted_at IS NULL")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryRoute {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.TIME)
    @Column(name = "id",columnDefinition = "uuid")
    private UUID id;

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
    @AttributeOverride(name = "hubId", column = @Column(name = "arrival_hub_id"))
    private HubId arrivalHubId;

    @Column(name = "sequence", nullable = false)
    private Integer sequence;

    //Todo 생성시 검증 로직 추가
    public static DeliveryRoute create(
            ShipperInfo shipperInfo,
            HubId departureHubId,
            HubId arrivalHubId,
            Integer sequence
    ) {
        DeliveryRoute route = new DeliveryRoute();
        route.shipperInfo = shipperInfo;
        route.departureHubId = departureHubId;
        route.arrivalHubId = arrivalHubId;
        route.sequence = sequence;
        return route;
    }



}
