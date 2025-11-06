package com.shoonglogitics.hubservice.domain.vo;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HubType {

    CENTRAL("중앙허브", true),
    NORMAL("일반허브", false);

    private final String description;
    private final boolean central;

    public boolean isCentral(){
        return central;
    }

    public boolean isNormal(){
        return !central;
    }

    public static HubType from(String type) {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("허브 타입은 필수입니다");
        }

        return Arrays.stream(values())
                .filter(t -> t.name().equalsIgnoreCase(type.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "유효하지 않은 허브 타입입니다: " + type
                ));
    }

    @Override
    public String toString() {
        return description;
    }
}
