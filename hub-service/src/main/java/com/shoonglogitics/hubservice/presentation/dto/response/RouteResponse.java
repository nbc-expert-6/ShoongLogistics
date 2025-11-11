package com.shoonglogitics.hubservice.presentation.dto.response;

import com.shoonglogitics.hubservice.application.dto.RouteResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RouteResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String routeType;
    private String routeDescription;
    private List<SegmentResponse> segments;
    private double totalDistanceKm;

    public static RouteResponse from(RouteResult result) {
        return RouteResponse.builder()
                .routeType(result.getRouteType())
                .routeDescription(getDescriptionFromType(result.getRouteType()))
                .segments(result.getSegments().stream()
                        .map(SegmentResponse::from)
                        .toList())
                .totalDistanceKm(Math.round(result.getTotalDistanceMeters() / 100.0) / 10.0)
                .build();
    }

    private static String getDescriptionFromType(String routeType) {
        return switch (routeType) {
            case "DIRECT" -> "직접배송";
            case "RELAY" -> "중계배송";
            case "CENTRAL_HUB" -> "중앙허브 경유";
            default -> routeType;
        };
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SegmentResponse implements Serializable {

        private static final long serialVersionUID = 1L;

        private String departureHubId;
        private String arrivalHubId;
        private double distanceKm;

        public static SegmentResponse from(RouteResult.RouteSegment segment) {
            return SegmentResponse.builder()
                    .departureHubId(segment.getDepartureHubId().toString())
                    .arrivalHubId(segment.getArrivalHubId().toString())
                    .distanceKm(Math.round(segment.getDistanceKm() * 10.0) / 10.0)
                    .build();
        }
    }
}