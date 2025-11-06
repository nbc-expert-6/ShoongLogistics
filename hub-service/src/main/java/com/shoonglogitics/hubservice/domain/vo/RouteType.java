package com.shoonglogitics.hubservice.domain.vo;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RouteType {

    DIRECT("직접배송", "중간 허브 없이 직접 배송"),
    RELAY("중계배송", "중간 허브를 거쳐 릴레이 배송"),
    CENTRAL_HUB("중앙허브 경유", "중앙허브를 통한 배송");

    private final String description;
    private final String detail;

    public boolean isDirect(){
        return this == DIRECT;
    }

    public boolean isRelay(){
        return this == RELAY;
    }

    public boolean isCentralHub(){
        return this == CENTRAL_HUB;
    }

    public static RouteType from(String type){
        if(type == null || type.isEmpty()){
            throw new IllegalArgumentException("경로 타입은 필수입니다");
        }
        return Arrays.stream(values())
                .filter(t -> t.name().equalsIgnoreCase(type.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "유효하지 않은 경로 타입니다: " + type
                ));
    }

    @Override
    public String toString() {
        return description;
    }

}
