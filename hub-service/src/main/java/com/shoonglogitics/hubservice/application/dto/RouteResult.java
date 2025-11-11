package com.shoonglogitics.hubservice.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shoonglogitics.hubservice.domain.vo.RouteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private String routeType; // RouteType -> String으로 변경
    private List<RouteSegment> segments;
    private int totalDistanceMeters;

    public static RouteResult createDirect(UUID fromId, UUID toId, int distanceMeters) {
        return RouteResult.builder()
                .routeType(RouteType.DIRECT.name()) // enum을 String으로
                .segments(List.of(new RouteSegment(fromId, toId, distanceMeters)))
                .totalDistanceMeters(distanceMeters)
                .build();
    }

    public static RouteResult createRelay(
            UUID fromId, UUID relayId, UUID toId,
            int firstLegDistance, int secondLegDistance) {

        return RouteResult.builder()
                .routeType(RouteType.RELAY.name()) // enum을 String으로
                .segments(List.of(
                        new RouteSegment(fromId, relayId, firstLegDistance),
                        new RouteSegment(relayId, toId, secondLegDistance)
                ))
                .totalDistanceMeters(firstLegDistance + secondLegDistance)
                .build();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RouteSegment implements Serializable {

        private static final long serialVersionUID = 1L;

        private UUID departureHubId;
        private UUID arrivalHubId;
        private int distanceMeters;

        @JsonIgnore
        public double getDistanceKm() {
            return distanceMeters / 1000.0;
        }
    }
}