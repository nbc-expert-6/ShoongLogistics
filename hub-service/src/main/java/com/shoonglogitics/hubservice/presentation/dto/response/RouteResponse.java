package com.shoonglogitics.hubservice.presentation.dto.response;

import com.shoonglogitics.hubservice.application.dto.RouteResult;
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
public class RouteResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID startHubId;
    private String startHubName;
    private UUID endHubId;
    private String endHubName;
    private int totalDistanceMeters;
    private int totalDurationMinutes;
    private List<WaypointResponse> waypoints;
    private List<RouteSegmentResponse> routes;

    public static RouteResponse from(RouteResult result) {
        return RouteResponse.builder()
                .startHubId(result.getStartHubId())
                .startHubName(result.getStartHubName())
                .endHubId(result.getEndHubId())
                .endHubName(result.getEndHubName())
                .totalDistanceMeters(result.getTotalDistanceMeters())
                .totalDurationMinutes(result.getTotalDurationMinutes())
                .waypoints(result.getWaypoints().stream()
                        .map(WaypointResponse::from)
                        .toList())
                .routes(result.getRoutes().stream()
                        .map(RouteSegmentResponse::from)
                        .toList())
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WaypointResponse implements Serializable {
        private static final long serialVersionUID = 1L;

        private int sequence;
        private UUID hubId;
        private String hubName;

        public static WaypointResponse from(RouteResult.Waypoint waypoint) {
            return WaypointResponse.builder()
                    .sequence(waypoint.getSequence())
                    .hubId(waypoint.getHubId())
                    .hubName(waypoint.getHubName())
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RouteSegmentResponse implements Serializable {
        private static final long serialVersionUID = 1L;

        private UUID departureHubId;
        private UUID arrivalHubId;
        private int distanceMeters;
        private int durationMinutes;

        public static RouteSegmentResponse from(RouteResult.RouteSegment segment) {
            return RouteSegmentResponse.builder()
                    .departureHubId(segment.getDepartureHubId())
                    .arrivalHubId(segment.getArrivalHubId())
                    .distanceMeters(segment.getDistanceMeters())
                    .durationMinutes(segment.getDurationMinutes())
                    .build();
        }
    }
}