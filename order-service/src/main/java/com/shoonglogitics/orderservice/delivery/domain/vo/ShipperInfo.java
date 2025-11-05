package com.shoonglogitics.orderservice.delivery.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ShipperInfo {
    private UUID shipperId;
    private String shipperName;
    private String shipperPhoneNumber;

    public static ShipperInfo of(UUID shipperId, String shipperName, String shipperPhoneNumber) {
        ShipperInfo shipperInfo = new ShipperInfo();
        shipperInfo.shipperId = shipperId;
        shipperInfo.shipperName = shipperName;
        shipperInfo.shipperPhoneNumber = shipperPhoneNumber;
        return shipperInfo;
    }
}
