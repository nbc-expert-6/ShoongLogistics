package com.shoonglogitics.hubservice.domain.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.shoonglogitics.hubservice.domain.common.BaseEntity;
import com.shoonglogitics.hubservice.domain.vo.Distance;
import com.shoonglogitics.hubservice.domain.vo.Duration;
import com.shoonglogitics.hubservice.domain.vo.HubId;
import com.shoonglogitics.hubservice.domain.vo.RouteType;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_hub_routes")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HubRoute extends BaseEntity {

	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	@Column(name = "id", columnDefinition = "uuid")
	private UUID id;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "departure_hub_id", nullable = false))
	private HubId departureHubId;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "arrival_hub_id", nullable = false))
	private HubId arrivalHubId;

	@Embedded
	@AttributeOverride(name = "meters", column = @Column(name = "distance_meters"))
	private Distance distanceMeters;

	@Embedded
	@AttributeOverride(name = "minutes", column = @Column(name = "duration_minutes"))
	private Duration durationMinutes;

	@Enumerated(EnumType.STRING)
	@Column(name = "route_type", nullable = false, length = 20)
	private RouteType routeType;

	public static HubRoute create(HubId departure, HubId arrival,
		Distance distance, Duration duration, RouteType type) {
		validate(departure, arrival, distance, duration);

		HubRoute route = new HubRoute();
		route.departureHubId = departure;
		route.arrivalHubId = arrival;
		route.distanceMeters = distance;
		route.durationMinutes = duration;
		route.routeType = type;
		return route;
	}

	private static void validate(HubId departure, HubId arrival, Distance distance, Duration duration) {
		if (departure.getValue().equals(arrival.getValue())) {
			throw new IllegalArgumentException("출발 허브와 도착 허브는 같을 수 없습니다.");
		}
		if (distance == null || distance.getMeters() <= 0) {
			throw new IllegalArgumentException("이동거리는 0보다 커야 합니다.");
		}
		if (duration == null || duration.getMinutes() <= 0) {
			throw new IllegalArgumentException("소요시간은 0보다 커야 합니다.");
		}
	}

}
