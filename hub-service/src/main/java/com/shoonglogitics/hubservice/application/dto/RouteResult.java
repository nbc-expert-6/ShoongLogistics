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

    private UUID startHubId;
    private String startHubName;
    private UUID endHubId;
    private String endHubName;
    private int totalDistanceMeters;
    private int totalDurationMinutes;
    private List<Waypoint> waypoints;
    private List<RouteSegment> routes;

    public static RouteResult createDirect(
            UUID fromId, String fromName,
            UUID toId, String toName,
            int distanceMeters) {

        int durationMinutes = calculateDuration(distanceMeters);

        List<Waypoint> waypoints = List.of(
                new Waypoint(1, fromId, fromName),
                new Waypoint(2, toId, toName)
        );

        List<RouteSegment> routes = List.of(
                new RouteSegment(1, fromId, toId, distanceMeters, durationMinutes)
        );

        return RouteResult.builder()
                .startHubId(fromId)
                .startHubName(fromName)
                .endHubId(toId)
                .endHubName(toName)
                .totalDistanceMeters(distanceMeters)
                .totalDurationMinutes(durationMinutes)
                .waypoints(waypoints)
                .routes(routes)
                .build();
    }

    public static RouteResult createRelay(
            UUID fromId, String fromName,
            UUID relayId, String relayName,
            UUID toId, String toName,
            int firstLegDistance, int secondLegDistance) {

        int firstLegDuration = calculateDuration(firstLegDistance);
        int secondLegDuration = calculateDuration(secondLegDistance);

        List<Waypoint> waypoints = List.of(
                new Waypoint(1, fromId, fromName),
                new Waypoint(2, relayId, relayName),
                new Waypoint(3, toId, toName)
        );

        List<RouteSegment> routes = List.of(
                new RouteSegment(1, fromId, relayId, firstLegDistance, firstLegDuration),
                new RouteSegment(2, relayId, toId, secondLegDistance, secondLegDuration)
        );

        return RouteResult.builder()
                .startHubId(fromId)
                .startHubName(fromName)
                .endHubId(toId)
                .endHubName(toName)
                .totalDistanceMeters(firstLegDistance + secondLegDistance)
                .totalDurationMinutes(firstLegDuration + secondLegDuration)
                .waypoints(waypoints)
                .routes(routes)
                .build();
    }
    private static int calculateDuration(int distanceMeters) {
        double distanceKm = distanceMeters / 1000.0;
        double hours = distanceKm / 60.0;
        return (int) Math.round(hours * 60);
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Waypoint implements Serializable {
        private static final long serialVersionUID = 1L;

        private int sequence;
        private UUID hubId;
        private String hubName;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RouteSegment implements Serializable {
        private static final long serialVersionUID = 1L;

        private int sequence;
        private UUID departureHubId;
        private UUID arrivalHubId;
        private int distanceMeters;
        private int durationMinutes;
    }
}