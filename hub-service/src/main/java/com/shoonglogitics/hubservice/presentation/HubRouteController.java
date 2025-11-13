package com.shoonglogitics.hubservice.presentation;

import com.shoonglogitics.hubservice.application.HubRouteService;
import com.shoonglogitics.hubservice.application.dto.RouteResult;
import com.shoonglogitics.hubservice.presentation.dto.response.RouteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/hub-routes")
@RequiredArgsConstructor
public class HubRouteController {
    private final HubRouteService hubRouteService;


    //허브 경로 계산
    @GetMapping("/optimal-route")
    public ResponseEntity<RouteResponse> calculateRoute(
            @RequestParam UUID departureId,
            @RequestParam UUID arrivalId
            ){
        RouteResult result = hubRouteService.calculateRoute(departureId, arrivalId);
        return ResponseEntity.ok(RouteResponse.from(result));
    }
}
