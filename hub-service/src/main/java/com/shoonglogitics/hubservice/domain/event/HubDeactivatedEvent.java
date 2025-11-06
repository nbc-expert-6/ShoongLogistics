package com.shoonglogitics.hubservice.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class HubDeactivatedEvent extends HubDomainEvent{

    private final UUID hubId;

    public HubDeactivatedEvent(UUID hubId) {
        super(LocalDateTime.now());
        this.hubId = hubId;
    }

}
