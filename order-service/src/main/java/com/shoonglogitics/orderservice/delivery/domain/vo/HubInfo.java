package com.shoonglogitics.orderservice.delivery.domain.vo;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class HubInfo {

    private UUID hubId;

    public static HubInfo of(UUID hubId) {
        HubInfo hub = new HubInfo();
        hub.hubId = hubId;
        return hub;
    }
}
