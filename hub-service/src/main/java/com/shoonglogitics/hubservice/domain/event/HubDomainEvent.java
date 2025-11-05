package com.shoonglogitics.hubservice.domain.event;

import java.time.LocalDateTime;

public class HubDomainEvent {
    private final LocalDateTime occurredAt;

    protected HubDomainEvent(LocalDateTime occurredAt) {this.occurredAt = LocalDateTime.now();}

}
