package com.shoonglogitics.hubservice.domain;

import com.shoonglogitics.hubservice.domain.entity.HubRoute;
import com.shoonglogitics.hubservice.domain.vo.Distance;
import com.shoonglogitics.hubservice.domain.vo.Duration;
import com.shoonglogitics.hubservice.domain.vo.HubId;
import com.shoonglogitics.hubservice.domain.vo.RouteType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Hub 도메인 테스트")
public class HubRouteTest {

    @Test
    @DisplayName("유효한 정보로 허브 경로를 생성한다")
    void createHubRoute_Success() {
        // given
        HubId departureHubId = HubId.of(UUID.randomUUID());
        HubId arrivalHubId = HubId.of(UUID.randomUUID());
        Distance distance = Distance.ofMeters(50000);
        Duration duration = Duration.ofMinutes(60);
        RouteType routeType = RouteType.DIRECT;

        // when
        HubRoute hubRoute = HubRoute.create(
                departureHubId,
                arrivalHubId,
                distance,
                duration,
                routeType
        );

        // then
        assertThat(hubRoute.getDepartureHubId()).isEqualTo(departureHubId);
        assertThat(hubRoute.getArrivalHubId()).isEqualTo(arrivalHubId);
        assertThat(hubRoute.getDistanceMeters()).isEqualTo(distance);
        assertThat(hubRoute.getDurationMinutes()).isEqualTo(duration);
        assertThat(hubRoute.getRouteType()).isEqualTo(routeType);
    }

    @Test
    @DisplayName("출발 허브와 도착 허브가 같으면 예외가 발생한다")
    void createHubRoute_WithSameDepartureAndArrival_ThrowsException() {
        // given
        UUID sameHubId = UUID.randomUUID();
        HubId departureHubId = HubId.of(sameHubId);
        HubId arrivalHubId = HubId.of(sameHubId);
        Distance distance = Distance.ofMeters(50000);
        Duration duration = Duration.ofMinutes(60);
        RouteType routeType = RouteType.DIRECT;

        // when & then
        assertThatThrownBy(() -> HubRoute.create(
                departureHubId,
                arrivalHubId,
                distance,
                duration,
                routeType
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출발 허브와 도착 허브는 같을 수 없습니다.");
    }

    @Test
    @DisplayName("이동거리가 null이면 예외가 발생한다")
    void createHubRoute_WithNullDistance_ThrowsException() {
        // given
        HubId departureHubId = HubId.of(UUID.randomUUID());
        HubId arrivalHubId = HubId.of(UUID.randomUUID());
        Distance distance = null;
        Duration duration = Duration.ofMinutes(60);
        RouteType routeType = RouteType.DIRECT;

        // when & then
        assertThatThrownBy(() -> HubRoute.create(
                departureHubId,
                arrivalHubId,
                distance,
                duration,
                routeType
        ))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이동거리는 0보다 커야 합니다.");
    }

    @Test
    @DisplayName("경유 경로를 생성한다")
    void createTransitRoute_Success() {
        // given
        HubId departureHubId = HubId.of(UUID.randomUUID());
        HubId arrivalHubId = HubId.of(UUID.randomUUID());
        Distance distance = Distance.ofMeters(80000);
        Duration duration = Duration.ofMinutes(120);

        // when
        HubRoute hubRoute = HubRoute.create(
                departureHubId,
                arrivalHubId,
                distance,
                duration,
                RouteType.DIRECT
        );

        // then
        assertThat(hubRoute.getRouteType()).isEqualTo(RouteType.DIRECT);
    }



}
