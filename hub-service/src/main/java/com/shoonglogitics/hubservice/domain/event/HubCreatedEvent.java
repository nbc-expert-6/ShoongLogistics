package com.shoonglogitics.hubservice.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class HubCreatedEvent extends HubDomainEvent{

    private final UUID hubId;
    private final String name;
    private final String address;
    private final Double latitude;
    private final Double longitude;

    public HubCreatedEvent(UUID hubId, String name, String address, Double latitude, Double longitude) {
        super(LocalDateTime.now());
        this.hubId = hubId;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
