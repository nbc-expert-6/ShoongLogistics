package com.shoonglogitics.hubservice.domain.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.shoonglogitics.hubservice.domain.common.BaseAggregateRoot;
import com.shoonglogitics.hubservice.domain.event.HubCreatedEvent;
import com.shoonglogitics.hubservice.domain.event.HubDeactivatedEvent;
import com.shoonglogitics.hubservice.domain.event.HubRenamedEvent;
import com.shoonglogitics.hubservice.domain.vo.Address;
import com.shoonglogitics.hubservice.domain.vo.GeoLocation;
import com.shoonglogitics.hubservice.domain.vo.HubType;
import com.shoonglogitics.hubservice.infrastructure.converter.GeoLocationConverter;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_hub")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hub extends BaseAggregateRoot<Hub> {

	@Id
	@UuidGenerator(style = UuidGenerator.Style.TIME)
	@Column(name = "id", columnDefinition = "uuid")
	private UUID id;

	@Column(nullable = false, length = 100, unique = true)
	private String name;

	@Embedded
	@AttributeOverride(name = "value", column = @Column(name = "address"))
	private Address address;

	@Column(name = "location", nullable = false, columnDefinition = "geometry(Point, 4326)")
	@Convert(converter = GeoLocationConverter.class)
	private GeoLocation location;

	@Enumerated(EnumType.STRING)
	@Column(name = "hub_type", nullable = false, length = 20)
	private HubType hubType;

	@Builder
	public Hub(String name, Address address, GeoLocation location, HubType hubType) {
		this.name = name;
		this.address = address;
		this.location = location;
		this.hubType = hubType;
	}

	public static Hub create(String name, String addressValue, Double latitude, Double longitude, HubType hubType) {
		Hub hub = Hub.builder()
			.name(name)
			.address(Address.of(addressValue))
			.location(GeoLocation.of(latitude, longitude))
			.hubType(hubType)
			.build();

		hub.registerEvent(new HubCreatedEvent(
			hub.getId(),
			name,
			addressValue,
			latitude,
			longitude
		));

		return hub;

	}

	public void update(String name) {
		String oldName = this.name;
		this.name = name;
		registerEvent(new HubRenamedEvent(this.id, oldName, name));
	}

	public void deactivate(Long userId) {
		if (this.isDeleted()) {
			throw new IllegalStateException("이미 삭제된 허브입니다.");
		}

		this.softDelete(userId);
		registerEvent(new HubDeactivatedEvent(this.id));
	}

	public boolean isCentralHub() {
		return this.hubType == HubType.CENTRAL;
	}

	public Double getLatitude() {
		return location != null ? location.getLatitude() : null;
	}

	public Double getLongitude() {
		return location != null ? location.getLongitude() : null;
	}

}
