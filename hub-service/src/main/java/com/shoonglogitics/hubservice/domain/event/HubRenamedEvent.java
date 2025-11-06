package com.shoonglogitics.hubservice.domain.event;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;

@Getter
public class HubRenamedEvent extends HubDomainEvent{

    private final UUID hubId;
    private final String oldName;
    private final String newName;

    public HubRenamedEvent(UUID hubId, String oldName, String newName){
        super(LocalDateTime.now());
        this.hubId = hubId;
        this.oldName = oldName;
        this.newName = newName;
    }

}
