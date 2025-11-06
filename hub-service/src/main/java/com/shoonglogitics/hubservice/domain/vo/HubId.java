package com.shoonglogitics.hubservice.domain.vo;

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

    private UUID value;

    private HubId(UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("유효하지 않은 허브 value 입니다");
        }
        this.value = value;
    }

    public static HubId of(UUID id) {
        return new HubId(id);
    }

}
