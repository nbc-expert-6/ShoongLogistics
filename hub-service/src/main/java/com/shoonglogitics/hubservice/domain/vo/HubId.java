package com.shoonglogitics.hubservice.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HubId {

    @Column(name = "hub_id")
    private UUID id;

    private HubId(UUID id) {
        if(id == null) {
            throw new IllegalArgumentException("유효하지 않은 허브 id 입니다");
        }
        this.id = id;
    }

    public static HubId of(UUID id){
        return new HubId(id);
    }

}
